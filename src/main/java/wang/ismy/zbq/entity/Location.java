package wang.ismy.zbq.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Location {

    private Integer locationId;

    private User user;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String address;

    private Boolean anonymous;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
