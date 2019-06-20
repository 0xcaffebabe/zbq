package wang.ismy.zbq.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.zbq.model.entity.user.User;

import java.time.LocalDateTime;

/**
 * 收藏抽象实体
 *
 * @author my
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Collection {

    private Integer collectionId;

    private Integer collectionType;

    private Integer contentId;

    private User user;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
