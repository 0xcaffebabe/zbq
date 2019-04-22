package wang.ismy.zbq.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;


public class LoginDTO {

    @NotBlank(message = "用户名不得为空")
    private String username;

    @NotBlank(message = "密码不得为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
