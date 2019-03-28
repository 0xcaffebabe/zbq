package wang.ismy.zbq.entity;

import lombok.Data;


public class Friend {

    private Integer friendUserId;

    private UserInfo friendUserInfo;

    public Integer getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(Integer friendUserId) {
        this.friendUserId = friendUserId;
    }

    public UserInfo getFriendUserInfo() {
        return friendUserInfo;
    }

    public void setFriendUserInfo(UserInfo friendUserInfo) {
        this.friendUserInfo = friendUserInfo;
    }
}
