package wang.ismy.zbq.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {

    private Integer messageId;

    private User fromUser;

    private User toUser;

    private String content;

    private Boolean hasRead;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
