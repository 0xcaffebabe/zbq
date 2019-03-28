package wang.ismy.zbq.enums;

public enum PermissionEnum {
    LOGIN("login"),PUBLISH_CONTENT("publishLogin");


    private String permission;

    public String getPermission() {
        return permission;
    }

    PermissionEnum(String permission) {
        this.permission = permission;
    }
}
