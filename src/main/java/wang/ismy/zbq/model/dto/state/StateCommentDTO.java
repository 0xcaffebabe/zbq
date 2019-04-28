package wang.ismy.zbq.model.dto.state;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class StateCommentDTO {

    @NotNull(message = "动态ID不得为空")
    private Integer stateId;

    private Integer toUser;

    @NotBlank(message = "评论内容不得为空")
    private String content;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getToUser() {
        return toUser;
    }

    public void setToUser(Integer toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
