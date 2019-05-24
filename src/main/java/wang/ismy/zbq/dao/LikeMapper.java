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

    List<ContentLikeDTO> countLikeByLikeTypeAndContentIdBatch(@Param("likeType") Integer likeType, @Param("contentIdList") List<Integer> contentIdList);

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
