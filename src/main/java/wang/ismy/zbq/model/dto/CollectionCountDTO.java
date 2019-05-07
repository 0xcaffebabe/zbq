package wang.ismy.zbq.model.dto;

import lombok.Data;

/**
 * @author my
 */
@Data
public class CollectionCountDTO {

    private Integer collectionId;

    private Long collectionCount;

    private Boolean hasCollect;
}
