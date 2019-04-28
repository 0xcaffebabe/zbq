package wang.ismy.zbq.model.entity.user;


import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UserPermission {

    private Integer userPermissionId;

    /**
     * 登录权限
     */
    private Boolean login = true;

    /**
     * 内容发布权限
     */

    private Boolean contentPublish = false;

    public Boolean coursePublish = false;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
