package wang.ismy.zbq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendAddDTO {


    private Integer friendAddId;

    private Integer fromUser;

    @NotNull(message = "添加好友to不得为空")
    private Integer toUser;

    private String msg;
}
