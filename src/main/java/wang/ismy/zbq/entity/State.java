package wang.ismy.zbq.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class State {

    private Integer stateId;

    private String content;

    private User user;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
