package wang.ismy.zbq.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class WebLog {

    private Long logId;

    private String ip;

    private String url;

    private String ua;

    private Integer user;

    private Integer timeConsuming;

    private LocalDateTime createTime;
}
