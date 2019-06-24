package wang.ismy.zbq.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户设置
 * @author chenj
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSetting {

    private Integer settingId;

    private User user;

    private String content;
}
