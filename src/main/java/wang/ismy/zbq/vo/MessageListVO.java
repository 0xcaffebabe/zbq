package wang.ismy.zbq.vo;

import wang.ismy.zbq.entity.user.UserInfo;


public class MessageListVO {


    private Integer oppositeSideId;

    private UserInfo oppositeSideUserInfo;

    private Integer msgCount;

    private String newestMsg;

    public Integer getOppositeSideId() {
        return oppositeSideId;
    }

    public void setOppositeSideId(Integer oppositeSideId) {
        this.oppositeSideId = oppositeSideId;
    }

    public UserInfo getOppositeSideUserInfo() {
        return oppositeSideUserInfo;
    }

    public void setOppositeSideUserInfo(UserInfo oppositeSideUserInfo) {
        this.oppositeSideUserInfo = oppositeSideUserInfo;
    }

    public Integer getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(Integer msgCount) {
        this.msgCount = msgCount;
    }

    public String getNewestMsg() {
        return newestMsg;
    }

    public void setNewestMsg(String newestMsg) {
        this.newestMsg = newestMsg;
    }
}
