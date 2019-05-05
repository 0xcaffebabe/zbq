package wang.ismy.zbq.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.StateMapper;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.dto.state.StateCommentDTO;
import wang.ismy.zbq.model.dto.state.StateDTO;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.model.entity.like.Likes;
import wang.ismy.zbq.model.entity.State;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.friend.FriendService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.model.vo.CommentVO;
import wang.ismy.zbq.model.vo.StateVO;
import wang.ismy.zbq.model.vo.user.UserVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author my
 */
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


    /**
     * 以当前用户的身份发布动态
     *
     * @param dto 动态DTO
     */
    public void publishState(StateDTO dto) {
        User user = userService.getCurrentUser();

        State state = new State();
        state.setUser(user);
        state.setContent(dto.getContent());

        if (stateMapper.insertNew(state) != 1) {
            ErrorUtils.error(R.STATE_PUBLISH_FAIL);
        }
    }

    /**
     * 拉取当前登录用户的动态
     *
     * @param page 分页组件
     * @return 动态视图列表
     */
    public List<StateVO> pullState(Page page) {
        User currentUser = userService.getCurrentUser();

        // 根据当前用户获取需要哪些用户的动态（用户本身+他的好友）
        List<Integer> stateUserIdList = getStateUserIdList(currentUser);
        var stateList = stateMapper.selectStateByUserIdBatchPaging(stateUserIdList, page);

        List<Integer> userIdList = new ArrayList<>();
        // 最终结果列表↓
        List<StateVO> stateVOList = new ArrayList<>();

        /*
         * 这两个map的作用是用来存储ID与VO的映射关系，
         * 在需要对应VO的时候，可以直接通过ID快速查找
         *
         */
        Map<Integer, StateVO> stateVOMap = new HashMap<>();
        Map<Integer, UserVO> userVOMap = new HashMap<>();

        /*
         * 循环内部将state实体转换成stateVO，
         * user转成VO，并将两个VO分别放入上面的map
         *
         */
        for (var i : stateList) {
            StateVO vo = new StateVO();
            UserVO userVO;
            if (userVOMap.get(i.getUser().getUserId()) != null) {
                userVO = userVOMap.get(i.getUser().getUserId());
            } else {
                userVO = new UserVO();
                userVO.setUserId(i.getUser().getUserId());
            }

            vo.setUserVO(userVO);
            userIdList.add(i.getUser().getUserId());
            BeanUtils.copyProperties(i, vo);
            stateVOList.add(vo);
            stateVOMap.put(vo.getUserVO().getUserId(), vo);
            userVOMap.put(userVO.getUserId(), userVO);
            vo.setSelf(vo.getUserVO().getUserId().equals(currentUser.getUserId()));

        }

        // 对userVOMap 中的每个VO 添加用户详细资料
        var userList = userService.selectByUserIdBatch(userIdList);

        for (var i : userList) {
            //TODO 这里的逻辑可以转换成使用UserVO的静态方法
            var vo = userVOMap.get(i.getUserId());
            if (vo != null) {
                vo.setProfile(i.getUserInfo().getProfile());
                vo.setNickName(i.getUserInfo().getNickName());
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

    /**
     * 以当前登录用户身份发布一条动态评论
     *
     * @param commentDTO 动态评论DTO
     * @return 受影响行数
     */
    public int publishComment(StateCommentDTO commentDTO) {

        User currentUser = userService.getCurrentUser();
        Comment comment = new Comment();
        comment.setCommentType(CommentTypeEnum.STATE.getCode());
        comment.setContent(commentDTO.getContent());
        comment.setFromUser(currentUser);
        comment.setTopicId(commentDTO.getStateId());
        comment.setToUser(userService.selectByPrimaryKey(commentDTO.getToUser()));

        return commentService.createNewCommentRecord(comment);
    }

    /**
     * 删除当前登录用户的动态（逻辑删除）
     *
     * @param stateId 动态ID
     * @return 受影响行数
     */
    public int deleteState(Integer stateId) {
        var state = stateMapper.selectByPrimaryKey(stateId);
        var currentUser = userService.getCurrentUser();
        if (state == null) {
            ErrorUtils.error(R.TARGET_STATE_NOT_EXIST);
        }

        if (!state.getUser().equals(currentUser)) {
            ErrorUtils.error(R.PERMISSION_DENIED);
        }

        return stateMapper.setInvisibleByPrimaryKey(stateId);
    }

    private List<Integer> getStateUserIdList(User currentUser) {
        var stateUserIdList = friendService.selectFriendListByUserId(currentUser.getUserId());
        stateUserIdList.add(currentUser.getUserId());
        return stateUserIdList;
    }

    private void addStateLikes(List<StateVO> stateVOList) {

        List<Integer> stateIdList = getStateIdList(stateVOList);

        var likeList = likeService.selectLikeListByLikeTypeAndContentIdBatch(LikeTypeEnum.STATE, stateIdList);


        Map<Integer, Likes> cacheMap = new HashMap<>();
        List<Integer> userIdList = new ArrayList<>();
        for (var i : likeList) {
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

            for (var j : likeList) {
                if (j.getLikeUser().equals(i)) {
                    j.setLikeUser(i);
                }
            }
        }

        User user = userService.getCurrentUser();
        for (var i : stateVOList) {
            i.setLikes(cacheMap.get(i.getStateId()));
            if (i.getLikes() == null) {
                Likes likes = new Likes();
                likes.setLikeType(0);
                likes.setLikeCount(0);
                likes.setContentId(i.getStateId());
                i.setLikes(likes);

            } else {
                for (var e : i.getLikes().getLikeList()) {
                    if (e.getLikeUser().equals(user)) {
                        i.getLikes().setHasLike(true);
                        break;
                    }
                }

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

        var commentList = commentService.selectComments(CommentTypeEnum.STATE, stateIdList);

        List<Integer> userIdList = new ArrayList<>();
        for (var i : commentList) {
            userIdList.add(i.getFromUser().getUserId());

            if (i.getToUser() != null) {
                userIdList.add(i.getToUser().getUserId());
            }
        }
        Map<Integer, User> userMap = new HashMap<>();

        Map<Integer, List<CommentVO>> commentVOMap = new HashMap<>();

        var userList = userService.selectByUserIdBatch(userIdList);

        for (var i : userList) {
            userMap.put(i.getUserId(), i);
        }

        for (var i : commentList) {
            var cl = commentVOMap.get(i.getTopicId());
            var vo = CommentVO.convert(i);

            if (cl == null) {
                cl = new ArrayList<>();
            }

            commentVOMap.put(i.getTopicId(), cl);
            vo.setFromUser(
                    UserVO.convert(userMap.get(vo.getFromUser().getUserId()))
            );

            if (vo.getToUser() != null) {
                vo.setToUser(
                        UserVO.convert(userMap.get(vo.getToUser().getUserId()))
                );
            }
            cl.add(vo);

        }

        for (var i : stateVOList) {
            i.setComments(commentVOMap.get(i.getStateId()));
        }


    }

}


