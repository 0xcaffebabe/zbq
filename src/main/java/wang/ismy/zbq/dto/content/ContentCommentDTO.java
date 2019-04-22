package wang.ismy.zbq.dto.content;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ContentCommentDTO {

    @NotNull(message = "内容ID不得为空")
    private Integer contentId;

    private Integer toUser;

    @NotBlank(message = "评论内容不得为空")
    private String content;
}
