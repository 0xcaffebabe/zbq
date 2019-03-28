package wang.ismy.zbq.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.zbq.entity.User;


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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
