package wang.ismy.zbq.service.friend;

import lombok.Setter;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wang.ismy.zbq.dao.friend.FriendMapper;
import wang.ismy.zbq.model.dto.FriendAddDTO;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.friend.Friend;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.vo.friend.FriendVO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.model.vo.friend.FriendAddVO;
import wang.ismy.zbq.model.vo.friend.RecommendFriendVO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
@Setter(onMethod_ = @Inject)
public class FriendService {


    private FriendMapper mapper;

    private UserService userService;

    private FriendAddService friendAddService;

    private ExecuteService executeService;

    public List<Friend> selectCurrentUserAllFriendPaging(Page page) {
        User user = userService.getCurrentUser();
        return mapper.selectFriendByUserIdPaging(user.getUserId(), page);
    }

    public List<Friend> selectCurrentUserFriendByNickName(String nickName) {
        User user = userService.getCurrentUser();
        return mapper.selectFriendByUserIdAndNickname(user.getUserId(), nickName);
    }

    public List<RecommendFriendVO> selectCurrentUserRecommendFriend() {
        User user = userService.getCurrentUser();

        // 查询出好友数量前2的用户
        List<Integer> hotUserId = mapper.selectTop2ByUserIdOrderByFriendCount(user.getUserId());

        var hotUser = userService.selectByUserIdBatch(hotUserId);

        var retList = hotUser
                .stream()
                .map(RecommendFriendVO::convert)
                .collect(Collectors.toList());
        retList.forEach(e -> e.setSource("热门用户"));


        retList.addAll(
                mapper.selectRecommendFriendByUserId(user.getUserId())
                        .stream().map(RecommendFriendVO::convert).collect(Collectors.toList())
        );

        for (var i : retList) {
            if (StringUtils.isEmpty(i.getSource())) {
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

        // 发送一条消息给接收方
        executeService.submit(() -> friendAddService.friendInform(friendAddDTO.getToUser(), user, friendAddDTO.getMsg()));
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
            vo.setUserId(i.getFromUser().getUserId());
            ret.add(vo);
        }

        return ret;
    }

    public int insertNewRelation(Integer user1, Integer user2) {
        return mapper.insert(user1, user2);
    }

    public boolean isFriend(Integer user1, Integer user2) {
        return mapper.selectFriendBy2User(user1, user2) != null;
    }


    public int countCurrentUserFriend() {
        User user = userService.getCurrentUser();

        return mapper.countByUserId(user.getUserId());
    }

    public List<Integer> selectFriendListByUserId(Integer userId) {

        return mapper.selectFriendListByUserId(userId);
    }

    /**
     * 获取朋友信息
     *
     * @param friendId 朋友ID
     * @return 朋友视图对象
     */
    public FriendVO getFriendInfo(Integer friendId) {
        var currentUser = userService.getCurrentUser();

        if (!isFriend(currentUser.getUserId(),friendId) ){
            ErrorUtils.error(R.NOT_FRIEND);
        }

        var friend = userService.selectByPrimaryKey(friendId);

        if (friend != null){
            FriendVO vo = new FriendVO();
            vo.setUserId(friendId);
            BeanUtils.copyProperties(friend.getUserInfo(),vo);
            vo.setJoinDate(friend.getUserInfo().getCreateTime().toLocalDate());
            return vo;
        }else{
            ErrorUtils.error(R.USERNAME_NOT_EXIST);
            return null;
        }

    }

    /**
     * 解除两个用户之间的好友关系
     * @param user1 用户1
     * @param user2 用户2
     */
    public void releaseFriend(Integer user1,Integer user2){

        // 如果某一方是系统通知账号，则拒绝删除
        if (user1 == 0 || user2 == 0){
            ErrorUtils.error(R.CAN_NOT_DELETE_SYSTEM_ACCOUNT);
        }

        if (!isFriend(user1,user2)){
            ErrorUtils.error(R.NOT_FRIEND);
        }

        if (mapper.deleteBy2User(user1,user2) != 2){
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }

    }
}
