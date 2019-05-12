package wang.ismy.zbq.model.vo;

import lombok.Data;
import wang.ismy.zbq.model.entity.Collection;

import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class CollectionVO {

    private Integer collectionId;

    private Integer contentId;

    private Integer collectionType;

    private String summary;

    private Long collectCount;

    private LocalDateTime createTime;

    public static CollectionVO convert(Collection collection){

        CollectionVO vo = new CollectionVO();

        vo.collectionId = collection.getCollectionId();
        vo.createTime = collection.getCreateTime();
        vo.contentId = collection.getContentId();
        vo.collectionType = collection.getCollectionType();
        return vo;
    }
}
