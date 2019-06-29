package wang.ismy.zbq.model.vo.friend;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author my
 */
@Data
public class FriendVO {

    private Integer userId;

    private String nickName;

    private String profile;

    private String description;

    private String region;

    private LocalDate birthday;

    private Integer penYear;

    private Integer gender;

    private LocalDate joinDate;


}
