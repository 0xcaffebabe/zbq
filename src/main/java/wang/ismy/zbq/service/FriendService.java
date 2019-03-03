package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.FriendMapper;
import wang.ismy.zbq.dto.FriendAddDTO;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.entity.Friend;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.vo.FriendAddVO;

import java.util.ArrayList;
import java.util.List;

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
        return friendMapper.selectFriendByUserIdPaging(user.getUserId(),page);
    }



    public List<Friend> selectCurrentUserFriendByNickName(String nickName) {
        User user = userService.getCurrentUser();
        return friendMapper.selectFriendByUserIdAndNickname(user.getUserId(), nickName);
    }

    public List<Friend> selectCurrentUserRecommendFriend() {
        User user = userService.getCurrentUser();
        return friendMapper.selectRecommendFriendByUserId(user.getUserId());
    }

    public List<Friend> selectStrangerByNickNamePaging(String nickName,Page page) {

        var list = userService.selectUserByUsernamePaging(nickName,page);
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

            ErrorUtils.error(StringResources.FRIEND_ADD_MSG_SENT);
        }


        if (isFriend(friendAddDTO.getFromUser(),friendAddDTO.getToUser())){
            ErrorUtils.error(StringResources.FRIEND_RELATION_CREATED);
        }
        return friendAddService.insertNew(friendAddDTO);
    }

    public List<FriendAddVO> selectCurrentUserFriendAddList() {
        User user = userService.getCurrentUser();

        var list = friendAddService.selectFriendAddListByToUser(user.getUserId());

        List<FriendAddVO> ret = new ArrayList<>();
        for (var i : list) {
            FriendAddVO vo = FriendAddVO.builder()
                    .friendAddId(i.getFriendAddId())
                    .userInfo(i.getFromUser().getUserInfo())
                    .msg(i.getMsg())
                    .createTime(i.getCreateTime()).build();
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


    public int countCurrentUserFriend(){
        User user = userService.getCurrentUser();

        return friendMapper.countByUserId(user.getUserId());
    }
}
