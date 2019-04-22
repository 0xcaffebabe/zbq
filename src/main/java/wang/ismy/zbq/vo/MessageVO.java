package wang.ismy.zbq.vo;

import wang.ismy.zbq.entity.user.UserInfo;

import java.time.LocalDateTime;


public class MessageVO {

    private Integer senderId;

    private UserInfo senderInfo;

    private String content;

    private LocalDateTime sendTime;

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public UserInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(UserInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
