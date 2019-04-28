package wang.ismy.zbq.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * @author my
 */
@Data
public class LocationDTO {

    @NotNull(message = "经度不得为空")
    private BigDecimal longitude;

    @NotNull(message = "纬度不得为空")
    private BigDecimal latitude;

    private String address;

    private Boolean anonymous = false;


}
