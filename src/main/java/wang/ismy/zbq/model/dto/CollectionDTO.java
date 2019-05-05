package wang.ismy.zbq.model.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author my
 */
@Data
public class CollectionDTO {

    @NotNull(message = "收藏类型不得为空")
    private Integer collectionType;

    @NotNull(message = "内容ID不得为空")
    private Integer contentId;
}
