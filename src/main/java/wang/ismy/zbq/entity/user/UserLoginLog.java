package wang.ismy.zbq.entity.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLoginLog {

    private Integer loginId;

    private User loginUser;

    private String loginIp;

    /**
    *  登录方式，0为用户名登录、1为邮箱登录
    *
    */
    private Integer loginType;

    private LocalDateTime createTime;

}
