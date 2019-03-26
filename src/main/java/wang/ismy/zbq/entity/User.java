package wang.ismy.zbq.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer userId;

    private String username;

    private String password;

    private UserInfo userInfo;

    private Permission permission;

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
}
