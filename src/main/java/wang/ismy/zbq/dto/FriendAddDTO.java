package wang.ismy.zbq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class FriendAddDTO {

    private Integer friendAddId;

    private Integer fromUser;

    @NotNull(message = "添加好友to不得为空")
    private Integer toUser;

    private String msg;

    public Integer getFriendAddId() {
        return friendAddId;
    }

    public void setFriendAddId(Integer friendAddId) {
        this.friendAddId = friendAddId;
    }

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
