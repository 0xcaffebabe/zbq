package wang.ismy.zbq.enums;

/**
 * @author my
 */
public enum ActionTypeEnum {

    /**
     * 点赞
     */
    LIKE(0),

    /**
     * 发布
     */
    PUBLISH(1),

    /**
     * 评论
     */
    COMMENT(2),

    /**
     * 收藏
     */
    COLLECTION(3);

    int code;

    ActionTypeEnum(int code) {
        this.code = code;
    }
}
