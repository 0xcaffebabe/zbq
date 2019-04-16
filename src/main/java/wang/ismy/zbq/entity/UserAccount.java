package wang.ismy.zbq.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class UserAccount {

    private Integer userAccountId;

    private User user;

    /**
    * 账户绑定类型，0为邮箱
    */
    private Integer accountType;

    /**
    * 第三方平台的用户ID，比如QQ号码/邮箱地址等
    */
    private String accountName;

    /**
    * 该绑定是否有效，比如邮箱是否经过验证或者是第三方账户失效
    */
    private Boolean valid;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
