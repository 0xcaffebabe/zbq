package wang.ismy.zbq.enums;

/**
 * @author my
 */

public enum PermissionEnum {

    /**
     * 登录权限
     */
    LOGIN("login"),
    /**
     * 内容发布权限
     */
    PUBLISH_CONTENT("contentPublish"),
    /**
     * 课程发布权限
     */
    COURSE_PUBLISH("coursePublish");


    private String permission;

    public String getPermission() {
        return permission;
    }

    PermissionEnum(String permission) {
        this.permission = permission;
    }
}
