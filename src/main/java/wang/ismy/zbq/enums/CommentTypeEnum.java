package wang.ismy.zbq.enums;

/**
 * @author MY
 */

public enum CommentTypeEnum {

    /**
     * 动态评论
     */
    STATE(0),

    /**
     * 内容评论
     */
    CONTENT(1);

    public int getCode() {
        return code;
    }

    private int code;

    CommentTypeEnum(int i) {

        code = i;
    }
}
