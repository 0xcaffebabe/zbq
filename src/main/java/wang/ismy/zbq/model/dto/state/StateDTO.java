package wang.ismy.zbq.model.dto.state;

import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @author my
 */
@Data
public class StateDTO {

    @NotBlank
    private String content;


}
