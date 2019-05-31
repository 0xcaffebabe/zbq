package wang.ismy.zbq.enums;

/**
 * @author my
 */

public enum InformTypeEnum {

    /**
     * 系统通知
     */
    SYSTEM_MESSAGE(0),

    // 邮件通知
    MAIL_MESSAGE(1),

    // 全部都通知一遍
    ALL(2)
    ;

    private int code;

    InformTypeEnum(int i) {
        code = i;
    }
}
