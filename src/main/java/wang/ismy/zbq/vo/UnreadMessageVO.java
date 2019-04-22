package wang.ismy.zbq.vo;

import wang.ismy.zbq.entity.user.UserInfo;

import java.time.LocalDateTime;


public class UnreadMessageVO {

    private Integer fromUserId;

    private UserInfo fromUserInfo;

    private Integer msgCount;

    private String newestMsg;

    private LocalDateTime sendTime;

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public UserInfo getFromUserInfo() {
        return fromUserInfo;
    }

    public void setFromUserInfo(UserInfo fromUserInfo) {
        this.fromUserInfo = fromUserInfo;
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

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
