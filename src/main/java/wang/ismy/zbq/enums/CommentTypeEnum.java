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
    CONTENT(1),

    /**
     * 章节评论
     */
    LESSON(2);

    public int getCode() {
        return code;
    }

    private int code;

    public static CommentTypeEnum of(int code){
        var values = CommentTypeEnum.values();
        for (var i : values){
            if (i.getCode() == code){
                return i;
            }
        }
        return null;
    }

    CommentTypeEnum(int i) {
        code = i;
    }
}
