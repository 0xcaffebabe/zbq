package wang.ismy.zbq.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author my
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionCountDTO {

    private Integer collectionId;

    private Long collectionCount;

    private Boolean hasCollect;
}
