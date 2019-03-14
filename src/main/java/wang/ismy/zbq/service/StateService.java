package wang.ismy.zbq.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.StateMapper;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.dto.StateDTO;
import wang.ismy.zbq.entity.Likes;
import wang.ismy.zbq.entity.State;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.vo.CommentVO;
import wang.ismy.zbq.vo.StateVO;
import wang.ismy.zbq.vo.UserVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StateService {

    @Autowired
    private UserService userService;

    @Autowired
    private StateMapper stateMapper;

    @Autowired
    private FriendService friendService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    public void currentUserPublishState(StateDTO dto) {
        User user = userService.getCurrentUser();

        State state = new State();
        state.setUser(user);
        state.setContent(dto.getContent());

        if (stateMapper.insertNew(state) != 1) {
            ErrorUtils.error(StringResources.STATE_PUBLISH_FAIL);
        }
    }

    public List<StateVO> selectCurrentUserStatePaging(Page page) {
        User currentUser = userService.getCurrentUser();
        var friendIdList = friendService.selectFriendListByUserId(currentUser.getUserId());
        friendIdList.add(currentUser.getUserId());
        var stateList = stateMapper.selectStateByUserIdBatchPaging(friendIdList, page);
        List<Integer> userIdList = new ArrayList<>();
        List<StateVO> stateVOList = new ArrayList<>();
        for (var i : stateList) {
            StateVO vo = new StateVO();
            UserVO userVO = new UserVO();
            userVO.setUserId(i.getUser().getUserId());
            vo.setUserVO(userVO);
            userIdList.add(i.getUser().getUserId());
            BeanUtils.copyProperties(i, vo);
            stateVOList.add(vo);
        }
        var userList = userService.selectByUserIdBatch(userIdList);

        for (var i : userList) {
            for (var j : stateVOList) {
                if (j.getUserVO().getUserId().equals(i.getUserId())) {
                    j.getUserVO().setProfile(i.getUserInfo().getProfile());
                    j.getUserVO().setNickName(i.getUserInfo().getNickName());
                }
            }
        }

        addStateLikes(stateVOList);
        addStateComment(stateVOList);
        return stateVOList;
    }

    public int countSelfState() {
        User user = userService.getCurrentUser();
        return stateMapper.countStateByUserId(user.getUserId());
    }

    private void addStateLikes(List<StateVO> stateVOList) {

        List<Integer> stateIdList = getStateIdList(stateVOList);

        var LikeList = likeService.selectLikeListByLikeTypeAndContentIdBatch(LikeTypeEnum.STATE, stateIdList);


        Map<Integer, Likes> cacheMap = new HashMap<>();
        List<Integer> userIdList = new ArrayList<>();
        for (var i : LikeList) {
            userIdList.add(i.getLikeUser().getUserId());
            if (cacheMap.get(i.getContentId()) != null) {
                var tmp = cacheMap.get(i.getContentId());
                tmp.getLikeList().add(i);
                tmp.setLikeCount(tmp.getLikeCount() + 1);
            } else {
                Likes likes = new Likes();
                likes.setLikeCount(1);
                likes.setContentId(i.getContentId());
                likes.setLikeType(LikeTypeEnum.STATE.getCode());
                likes.getLikeList().add(i);
                cacheMap.put(i.getContentId(), likes);
            }

        }

        var userList = userService.selectByUserIdBatch(userIdList);

        for (var i : userList) {

            for (var j : LikeList) {
                if (j.getLikeUser().equals(i)) {
                    j.setLikeUser(i);
                }
            }
        }

        for (var i : stateVOList) {
            i.setLikes(cacheMap.get(i.getStateId()));
            if (i.getLikes() == null) {
                Likes likes = new Likes();
                likes.setLikeType(0);
                likes.setLikeCount(0);
                likes.setContentId(i.getStateId());
                i.setLikes(likes);

            }
        }


    }

    private List<Integer> getStateIdList(List<StateVO> stateVOList) {
        List<Integer> stateIdList = new ArrayList<>();
        for (var i : stateVOList) {
            stateIdList.add(i.getStateId());
        }
        return stateIdList;
    }

    private void addStateComment(List<StateVO> stateVOList) {
        var stateIdList = getStateIdList(stateVOList);

        var CommentList = commentService.selectCommentByCommentTypeAndTopicId(CommentTypeEnum.STATE, stateIdList);

        List<Integer> userIdList = new ArrayList<>();
        for (var i : CommentList) {
            userIdList.add(i.getFromUser().getUserId());

            if (i.getToUser() != null) {
                userIdList.add(i.getToUser().getUserId());
            }
        }
        Map<Integer, User> userMap = new HashMap<>();

        Map<Integer,List<CommentVO>> commentVOMap = new HashMap<>();

        var userList = userService.selectByUserIdBatch(userIdList);

        for (var i : userList){
            userMap.put(i.getUserId(),i);
        }

        for (var i : CommentList){
            var cl = commentVOMap.get(i.getTopicId());
            var vo = CommentVO.convert(i);

            if (cl == null) {
                cl = new ArrayList<>();
            }

            commentVOMap.put(i.getTopicId(),cl);
            vo.setFromUser(
                    UserVO.conver(userMap.get(vo.getFromUser().getUserId()))
            );

            if (vo.getToUser() != null){
                vo.setToUser(
                        UserVO.conver(userMap.get(vo.getToUser().getUserId()))
                );
            }
            cl.add(vo);

        }

        for (var i : stateVOList){
            i.setComments(commentVOMap.get(i.getStateId()));
        }


    }




}


