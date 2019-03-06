package wang.ismy.zbq.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MessageDTO {

    @NotNull(message = "发送对象不得为空")
    private Integer to;

    @NotBlank(message = "消息内容不得为空")
    private String content;
}
