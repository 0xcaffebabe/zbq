package wang.ismy.zbq.model.dto.user;

import lombok.Data;
import wang.ismy.zbq.enums.UserAccountEnum;

/**
 * @author my
 */
@Data
public class UserAccountDTO {

    private Integer userId;

    private String accountName;

    private UserAccountEnum accountType;
}
