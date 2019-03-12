package wang.ismy.zbq.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StateDTO {

    @NotBlank
    private String content;
}
