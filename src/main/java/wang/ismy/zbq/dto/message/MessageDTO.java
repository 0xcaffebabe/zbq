package wang.ismy.zbq.dto.message;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author my
 */
@Data
public class MessageDTO {

    @NotNull(message = "发送对象不得为空")
    private Integer to;

    @NotBlank(message = "消息内容不得为空")
    private String content;


}
