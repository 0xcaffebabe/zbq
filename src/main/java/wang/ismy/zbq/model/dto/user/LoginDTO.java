package wang.ismy.zbq.model.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @author my
 */
@Data
public class LoginDTO {

    @NotBlank(message = "用户名不得为空")
    private String username;

    @NotBlank(message = "密码不得为空")
    private String password;

}
