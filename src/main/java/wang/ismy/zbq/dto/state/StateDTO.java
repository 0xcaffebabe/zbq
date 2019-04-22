package wang.ismy.zbq.dto.state;

import lombok.Data;

import javax.validation.constraints.NotBlank;


public class StateDTO {

    @NotBlank
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
