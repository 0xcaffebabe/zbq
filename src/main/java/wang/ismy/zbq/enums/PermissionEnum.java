package wang.ismy.zbq.enums;

public enum PermissionEnum {
    LOGIN("login"),PUBLISH_CONTENT("contentPublish"),COURSE_PUBLISH("coursePublish");


    private String permission;

    public String getPermission() {
        return permission;
    }

    PermissionEnum(String permission) {
        this.permission = permission;
    }
}
