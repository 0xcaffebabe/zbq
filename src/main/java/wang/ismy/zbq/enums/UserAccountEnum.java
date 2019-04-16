package wang.ismy.zbq.enums;

/**
 * @author my
 */

public enum UserAccountEnum {
    // 邮箱
    EMAIL(0);
    private Integer code;

    UserAccountEnum(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
