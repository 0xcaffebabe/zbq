package wang.ismy.zbq.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.entity.Location;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LocationVO {

    private UserVO userVO;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String address;

    private Boolean anonymous;

    private LocalDateTime createTime;

    public static LocationVO convert(Location location){

        LocationVO vo = new LocationVO();
        BeanUtils.copyProperties(location,vo);
        if (location.getAnonymous() != null){
            if (!location.getAnonymous()){
                UserVO tmpUserVO = new UserVO();
                tmpUserVO.setUserId(location.getUser().getUserId());
            }
        }
        return vo;
    }

}
