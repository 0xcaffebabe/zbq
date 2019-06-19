package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.dto.content.ContentLikeDTO;
import wang.ismy.zbq.model.entity.like.Like;

import java.util.List;
import java.util.Map;

public interface LikeMapper {

    int insertNew(Like like);

    List<Like> selectLikeListByLikeTypeAndContentId(@Param("likeType") int likeType, @Param("contentId") Integer contentId);

    List<Like> selectLikeListByLikeTypeAndContentIdBatch(@Param("likeType") Integer likeType, @Param("contentIdList") List<Integer> contentIdList);

    Like selectLikeByLikeTypeAndContentIdAndUserId(@Param("likeType") Integer likeType, @Param("contentId") Integer contentId,
                                                   @Param("userId") Integer userId);

    int deleteLikeByLikeTypeAndContentIdAndUserId(@Param("likeType") Integer likeType, @Param("contentId") Integer contentId,
                                                  @Param("userId") Integer userId);

    long countStateLikeByUserId(Integer userId);

    long countContentLikeByUserId(Integer userId);

    /**
     * 根据点赞类型及内容ID列表批量获取点赞数据
     *
     * @param likeType      点赞类型
     * @param contentIdList 内容ID列表
     * @return 内容点赞对象列表
     */
    List<ContentLikeDTO> countLikeByLikeTypeAndContentIdBatch(@Param("likeType") Integer likeType, @Param("contentIdList") List<Integer> contentIdList);

    /**
     * 批量查询某一用户是否点赞某一内容
     *
     * @param likeType      点赞类型
     * @param userId        用户ID
     * @param contentIdList 内容ID列表
     * @return 查询结果键值对列表
     */
    List<Map<String, Object>> selectHasLikeByLikeTypeAndContentIdAndUserIdBatch(@Param("likeType") Integer likeType, @Param("contentIdList") List<Integer> contentIdList, @Param("userId") Integer userId);

    /**
     * 根据用户分页查询点赞列表（根据时间降序）
     *
     * @param userId 用户ID
     * @param page   分页组件
     * @return 点赞列表
     */
    List<Like> selectLikeListByUserPaging(@Param("user") Integer userId, @Param("page") Page page);
}
