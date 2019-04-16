package wang.ismy.zbq.dto;

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
