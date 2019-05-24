package wang.ismy.zbq.enums;

/**
 * @author my
 */

public enum LikeTypeEnum {

    /**
     * 未定义
     */
    UNDEFINED(-1),
    /**
     * 动态点赞
     */
    STATE(0),

    /**
     * 内容点赞
     */
    CONTENT(1);

    public int getCode() {
        return code;
    }

    private int code;

    LikeTypeEnum(int i) {

        code = i;
    }

    public static LikeTypeEnum valueOf(int code) {

        for (var i : values()) {
            if (i.getCode() == code) {
                return i;
            }
        }
        return UNDEFINED;
    }
}
