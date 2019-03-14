package wang.ismy.zbq.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.zbq.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {

    private Integer userId;

    private String nickName;

    private String profile;

    public static UserVO conver(User user){
        if (user == null){
            return null;
        }
        return UserVO.builder()
                .nickName(user.getUserInfo().getNickName())
                .profile(user.getUserInfo().getProfile())
                .userId(user.getUserId()).build();
    }
}
