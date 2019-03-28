package wang.ismy.zbq.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.zbq.entity.UserInfo;

import java.time.LocalDateTime;


public class FriendAddVO {

    private Integer friendAddId;

    private UserInfo userInfo; // 请求添加放用户信息

    private String msg;


    private LocalDateTime createTime;

    public Integer getFriendAddId() {
        return friendAddId;
    }

    public void setFriendAddId(Integer friendAddId) {
        this.friendAddId = friendAddId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
