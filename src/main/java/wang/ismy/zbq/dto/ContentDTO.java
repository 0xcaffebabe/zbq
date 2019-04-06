package wang.ismy.zbq.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ContentDTO {

    @NotBlank(message = "标题不得为空")
    private String title;

    @NotBlank(message = "内容不得为空")
    private String content;
    @NotBlank(message = "标签不得为空")
    private String tags;

}
