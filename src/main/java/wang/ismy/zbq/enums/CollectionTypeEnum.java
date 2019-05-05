package wang.ismy.zbq.enums;

/**
 * @author my
 */

public enum CollectionTypeEnum {

    // 视频类型
    VIDEO(0),
    // 内容类型
    CONTENT(1),
    // 课程类型
    COURSE(2);

    private int code;


    CollectionTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
