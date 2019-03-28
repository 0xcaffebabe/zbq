package wang.ismy.zbq.entity;


import lombok.Data;

import java.time.LocalDateTime;


public class UserPermission {

    private Integer userPermissionId;

    private Boolean login = true; // 登录权限

    private Boolean contentPublish =false;/* 内容发布权限 */;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Integer getUserPermissionId() {
        return userPermissionId;
    }

    public void setUserPermissionId(Integer userPermissionId) {
        this.userPermissionId = userPermissionId;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public Boolean getContentPublish() {
        return contentPublish;
    }

    public void setContentPublish(Boolean contentPublish) {
        this.contentPublish = contentPublish;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
