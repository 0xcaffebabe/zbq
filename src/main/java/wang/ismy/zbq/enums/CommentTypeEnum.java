package wang.ismy.zbq.enums;

public enum CommentTypeEnum {

    STATE(0);

    public int getCode() {
        return code;
    }

    private int code;

    CommentTypeEnum(int i) {

        code = i;
    }
}