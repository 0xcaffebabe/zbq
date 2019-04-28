package wang.ismy.zbq.model.dto.message;

import lombok.Data;

import java.time.LocalDateTime;


public class UnreadMessageDTO {

    private Integer fromUser;

    private Integer msgCount;

    private String newestMsg;

    private LocalDateTime sendTime;

    public Integer getFromUser() {
        return fromUser;
    }

    public void setFromUser(Integer fromUser) {
        this.fromUser = fromUser;
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
