package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.Content;

import java.util.List;

public interface ContentMapper {

    int insertNew(Content content);

    List<Content> selectContentListPaging(@Param("page") Page page);

    Content selectByPrimaryKey(Integer contentId);

    /**
     * 批量查询内容
     *
     * @param list 内容ID列表
     * @return 内容列表
     */
    List<Content> selectBatch(List<Integer> list);

    /**
     * 分页根据用户查询（根据时间降序）
     *
     * @param userId 用户ID
     * @param page   分页组件
     * @return 内容列表
     */
    List<Content> selectByUserIdPaging(@Param("user") Integer userId, @Param("page") Page page);
}
