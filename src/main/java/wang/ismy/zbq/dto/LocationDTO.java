package wang.ismy.zbq.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


public class LocationDTO {

    @NotNull(message = "经度不得为空")
    private BigDecimal longitude;

    @NotNull(message = "纬度不得为空")
    private BigDecimal latitude;

    private String address;

    private Boolean anonymous = false;

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
}
