package wang.ismy.zbq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
public class UserInfoDTO {

    private String nickName;

    private String profile;

    private LocalDate birthday;

    private Integer penYear;

    private String region;

    private String email;

    @Max(1)
    @Min(-1)
    private Integer gender;

    private String description;

}
