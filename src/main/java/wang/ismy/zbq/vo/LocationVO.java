package wang.ismy.zbq.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.entity.Location;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
