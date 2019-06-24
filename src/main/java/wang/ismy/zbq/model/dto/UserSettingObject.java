package wang.ismy.zbq.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author chenj
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSettingObject {

    /**
     * 邮箱通知设置
     */
    @NotNull
    private Boolean emailInform;

    /**
     * 匿名设置
     */
    @NotNull
    private Boolean anonymous;

    public static UserSettingObject empty(){
        UserSettingObject obj = new UserSettingObject();
        obj.setEmailInform(true);
        obj.setAnonymous(false);
        return obj;
    }
}
