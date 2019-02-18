package wang.ismy.zbq.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {

    @NotBlank(message = "用户名不得为空")
    private String username;

    @NotBlank(message = "密码不得为空")
    private String password;
}
