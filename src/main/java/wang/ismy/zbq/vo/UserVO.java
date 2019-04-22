package wang.ismy.zbq.vo;

import lombok.Data;
import wang.ismy.zbq.entity.user.User;


/**
 * @author my
 */
@Data
public class UserVO {

    private Integer userId;

    private String nickName;

    private String profile;

    public static UserVO convert(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();


        userVO
                .setNickName(user.getUserInfo().getNickName());
        userVO.setProfile(user.getUserInfo().getProfile());
        userVO.setUserId(user.getUserId());

        return userVO;
    }


}
