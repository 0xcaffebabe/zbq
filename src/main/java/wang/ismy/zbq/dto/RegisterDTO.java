package wang.ismy.zbq.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


public class RegisterDTO {

    @NotBlank(message = "用户名不得为空")
    @Length(max = 20,message = "用户名长度不得超过20")
    private String username;

    @NotBlank(message = "密码不得为空")
    @Length(min = 32,max = 32,message = "密码格式不正确")
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
