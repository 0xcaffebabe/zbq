package wang.ismy.zbq.model.vo.user;

import lombok.Data;
import wang.ismy.zbq.model.UserIdGetter;
import wang.ismy.zbq.model.entity.user.User;


/**
 * @author my
 */
@Data
public class UserVO implements UserIdGetter {

    private Integer userId;

    private String nickName;

    private String profile;

    private String region;

    private Integer penYear;

    public static UserVO convert(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setNickName(user.getUserInfo().getNickName());
        userVO.setProfile(user.getUserInfo().getProfile());
        userVO.setUserId(user.getUserId());
        userVO.setRegion(user.getUserInfo().getRegion());
        userVO.setPenYear(user.getUserInfo().getPenYear());
        return userVO;
    }


    public static UserVO empty() {
        UserVO userVO = new UserVO();
        userVO.setUserId(-1);
        return userVO;
    }
}
