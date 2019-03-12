package wang.ismy.zbq.enums;

public enum LikeTypeEnum {


    STATE(0);

    public int getCode() {
        return code;
    }

    private int code;
    LikeTypeEnum(int i) {

        code = i;
    }
}
