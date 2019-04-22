package wang.ismy.zbq.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.entity.friend.Friend;
import wang.ismy.zbq.entity.user.User;

/**
 * @author my
 */
@Data
public class RecommendFriendVO {

    private Integer userId;

    private String nickName;

    private String profile;

    private String source;

    private Integer gender;

    private String region;

    private Integer penYear;



    public static RecommendFriendVO convert(Friend friend){

        RecommendFriendVO vo = new RecommendFriendVO();
        vo.userId = friend.getFriendUserId();

        BeanUtils.copyProperties(friend.getFriendUserInfo(),vo);
        return vo;
    }

    public static RecommendFriendVO convert(User user){
        RecommendFriendVO vo = new RecommendFriendVO();
        vo.setUserId(user.getUserId());
        BeanUtils.copyProperties(user.getUserInfo(),vo);
        return vo;
    }

}
