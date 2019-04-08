package wang.ismy.zbq.entity;


import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UserPermission {

    private Integer userPermissionId;

    private Boolean login = true; // 登录权限

    private Boolean contentPublish =false;/* 内容发布权限 */;

    public Boolean coursePublish = false;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
