package wang.ismy.zbq.model.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author my
 */
@Data
public class PasswordResetDTO {

    @Email
    private String email;

    @NotNull
    private String code;

    @NotNull
    private String password;
}
