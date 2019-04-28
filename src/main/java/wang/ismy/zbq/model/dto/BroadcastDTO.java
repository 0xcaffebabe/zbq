package wang.ismy.zbq.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author my
 */
@Data
public class BroadcastDTO {

    @NotBlank(message = "广播消息不得为空")
    private String content;

    private Boolean anonymous = false;
}
