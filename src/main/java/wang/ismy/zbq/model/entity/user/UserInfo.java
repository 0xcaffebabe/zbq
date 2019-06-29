package wang.ismy.zbq.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author my
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Integer userInfoId;

    private String nickName;

    private String profile;

    private LocalDate birthday;

    private Integer penYear;

    private String region;

    /**
     * 1代表男
     * -1代表女
     * 0代表未知
     */
    private Integer gender;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
