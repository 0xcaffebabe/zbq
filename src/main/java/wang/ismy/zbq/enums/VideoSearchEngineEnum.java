package wang.ismy.zbq.enums;


import lombok.Getter;

/**
 * @author my
 */
@Getter
public enum VideoSearchEngineEnum {

    /**
     * 未知搜索引擎
     */
    UNKNOWN(-1,"未知引擎"),
    /**
     * B站
     */
    BILI_BILI(0,"B站"),
    /**
     * 百度
     */
    BAI_DU(1,"百度"),
    /**
     * 搜狗
     */
    SO_GOU(2,"搜狗"),
    /**
     * 优酷
     */
    YOU_KU(3,"优酷");
    private int code;

    private String engineName;

    VideoSearchEngineEnum(int code,String engineName) {
        this.code = code;
        this.engineName = engineName;
    }

    public static VideoSearchEngineEnum valueOf(Integer code) {

        var values = values();

        for (var i : values) {
            if (i.code == code) {
                return i;
            }
        }

        return UNKNOWN;
    }


}
