package wang.ismy.zbq.model.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


/**
 * @author my
 */
@Data
public class RegisterDTO {

    @NotBlank(message = "用户名不得为空")
    @Length(max = 20,message = "用户名长度不得超过20")
    private String username;

    @NotBlank(message = "密码不得为空")
    @Length(min = 32,max = 32,message = "密码格式不正确")
    private String password;


}
