package wang.ismy.zbq.dto.message;

import lombok.Data;


public class MessageListDTO {

    private Integer fromUser;

    private Integer toUser;

    private String newestMsg;

    public Integer getFromUser() {
        return fromUser;
    }

    public void setFromUser(Integer fromUser) {
        this.fromUser = fromUser;
    }

    public Integer getToUser() {
        return toUser;
    }

    public void setToUser(Integer toUser) {
        this.toUser = toUser;
    }

    public String getNewestMsg() {
        return newestMsg;
    }

    public void setNewestMsg(String newestMsg) {
        this.newestMsg = newestMsg;
    }
}
