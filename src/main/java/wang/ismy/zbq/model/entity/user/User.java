package wang.ismy.zbq.model.entity.user;

import lombok.Data;
import wang.ismy.zbq.model.UserIdGetter;

import java.time.LocalDateTime;


/**
 * @author my
 */
@Data
public class User implements UserIdGetter {

    private Integer userId;

    private String username;

    private String password;

    private UserInfo userInfo;

    private UserPermission userPermission;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime lastLogin;


    @Override
    public int hashCode(){
        return userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }

        if (obj instanceof User){
            var i = (User)obj;
            return i.getUserId().equals(userId);
        }else{
            return false;
        }
    }

    public static User convert(Integer userId){
        User user = new User();
        user.setUserId(userId);
        return user;
    }
}
