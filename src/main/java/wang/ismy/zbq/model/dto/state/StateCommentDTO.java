package wang.ismy.zbq.model.dto.state;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author my
 */
@Data
public class StateCommentDTO {

    @NotNull(message = "动态ID不得为空")
    private Integer stateId;

    private Integer toUser;

    @NotBlank(message = "评论内容不得为空")
    private String content;


}
