package wang.ismy.zbq.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer userId;

    private String username;

    private String password;

    private UserInfo userInfo;

    private Permission permission;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
