package wang.ismy.zbq.service.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wang.ismy.zbq.dao.friend.FriendMapper;
import wang.ismy.zbq.dto.FriendAddDTO;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.entity.friend.Friend;
import wang.ismy.zbq.entity.user.User;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.vo.FriendAddVO;
import wang.ismy.zbq.vo.RecommendFriendVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private FriendAddService friendAddService;

    public List<Friend> selectCurrentUserAllFriendPaging(Page page) {
        User user = userService.getCurrentUser();
        return friendMapper.selectFriendByUserIdPaging(user.getUserId(), page);
    }

    public List<Friend> selectCurrentUserFriendByNickName(String nickName) {
        User user = userService.getCurrentUser();
        return friendMapper.selectFriendByUserIdAndNickname(user.getUserId(), nickName);
    }

    public List<RecommendFriendVO> selectCurrentUserRecommendFriend() {
        User user = userService.getCurrentUser();

        // 查询出好友数量前2的用户
        List<Integer> hotUserId = friendMapper.selectTop2ByUserIdOrderByFriendCount(user.getUserId());

        var hotUser = userService.selectByUserIdBatch(hotUserId);

        var retList = hotUser
                .stream()
                .map(RecommendFriendVO::convert)
                .collect(Collectors.toList());
        retList.forEach(e->e.setSource("热门用户"));


        retList.addAll(
                friendMapper.selectRecommendFriendByUserId(user.getUserId())
                        .stream().map(RecommendFriendVO::convert).collect(Collectors.toList())
        );

        for (var i :retList){
            if (StringUtils.isEmpty(i.getSource())){
                i.setSource("随机推荐");
            }
        }
        return retList;
    }

    public List<Friend> selectStrangerByNickNamePaging(String nickName, Page page) {

        var list = userService.selectUserByUsernamePaging(nickName, page);
        var ret = new ArrayList<Friend>();
        User user = userService.getCurrentUser();

        list.forEach(u -> {
            if (!u.getUserId().equals(user.getUserId())) {
                Friend friend = new Friend();
                friend.setFriendUserId(u.getUserId());
                friend.setFriendUserInfo(u.getUserInfo());
                ret.add(friend);
            }
        });

        return ret;
    }

    public int sendAFriendAddMsg(FriendAddDTO friendAddDTO) {
        User user = userService.getCurrentUser();

        friendAddDTO.setFromUser(user.getUserId());
        if (friendAddService.selectFriendAddByFromUserAndToUser(user.getUserId(), friendAddDTO.getToUser()) != null) {

            ErrorUtils.error(R.FRIEND_ADD_MSG_SENT);
        }


        if (isFriend(friendAddDTO.getFromUser(), friendAddDTO.getToUser())) {
            ErrorUtils.error(R.FRIEND_RELATION_CREATED);
        }
        return friendAddService.insertNew(friendAddDTO);
    }

    public List<FriendAddVO> selectCurrentUserFriendAddList() {
        User user = userService.getCurrentUser();

        var list = friendAddService.selectFriendAddListByToUser(user.getUserId());

        List<FriendAddVO> ret = new ArrayList<>();
        for (var i : list) {
            FriendAddVO vo = new FriendAddVO();
            vo.setFriendAddId(i.getFriendAddId());
            vo.setUserInfo(i.getFromUser().getUserInfo());
            vo.setMsg(i.getMsg());
            vo.setVisible(i.getVisible());
            vo.setCreateTime(i.getCreateTime());
            ret.add(vo);
        }

        return ret;
    }

    public int insertNewRelation(Integer user1, Integer user2) {
        return friendMapper.insert(user1, user2);
    }

    public boolean isFriend(Integer user1, Integer user2) {
        return friendMapper.selectFriendBy2User(user1, user2) != null;
    }


    public int countCurrentUserFriend() {
        User user = userService.getCurrentUser();

        return friendMapper.countByUserId(user.getUserId());
    }

    public List<Integer> selectFriendListByUserId(Integer userId) {

        return friendMapper.selectFriendListByUserId(userId);
    }
}
