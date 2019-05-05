package wang.ismy.zbq.model.entity;


import lombok.Data;
import wang.ismy.zbq.model.entity.user.User;

import java.time.LocalDateTime;

/**
 * 收藏抽象实体
 *
 * @author my
 */
@Data
public class Collection {

    private Integer collectionId;

    private Integer collectionType;

    private Integer contentId;

    private User user;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
