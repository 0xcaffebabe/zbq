package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.entity.Collection;

/**
 * @author my
 */
public interface CollectionMapper {


    /**
     * 新增一条收藏
     *
     * @param collection 收藏实体
     * @return 受影响行数
     */
    int insertNew(Collection collection);


    /**
     * 根据收藏类型,内容ID,用户ID查询
     *
     * @param type      收藏类型
     * @param contentId 内容ID
     * @param userId    用户ID
     * @return 收藏实体
     */
    Collection selectByTypeAndContentIdAndUserId(@Param("type") Integer type,
                                                 @Param("contentId") Integer contentId, @Param("userId") Integer userId);
}
