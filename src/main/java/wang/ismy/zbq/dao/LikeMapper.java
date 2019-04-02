package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.dto.ContentLikeDTO;
import wang.ismy.zbq.entity.Like;
import wang.ismy.zbq.enums.LikeTypeEnum;

import java.util.List;
import java.util.Map;

public interface LikeMapper {

    int insertNew(Like like);

    List<Like> selectLikeListByLikeTypeAndContentId(@Param("likeType") int likeType, @Param("contentId") Integer contentId);

    List<Like> selectLikeListByLikeTypeAndContentIdBatch(@Param("likeType") Integer likeType, @Param("contentIdList") List<Integer> contentIdList);

    Like selectLikeByLikeTypeAndContentIdAndUserId(@Param("likeType") Integer likeType,@Param("contentId") Integer contentId,
                                                   @Param("userId") Integer userId);

    int deleteLikeByLikeTypeAndContentIdAndUserId(@Param("likeType") Integer likeType,@Param("contentId") Integer contentId,
                                                  @Param("userId") Integer userId);

    long countStateLikeByUserId(Integer userId);

    long countContentLikeByUserId(Integer userId);

    List<ContentLikeDTO> countLikeByLikeTypeAndContentIdBatch(@Param("likeType") Integer likeType, @Param("contentIdList") List<Integer> contentIdList);

    List<Map<String,Object>> selectHasLikeByLikeTypeAndContentIdAndUserIdBatch(@Param("likeType") Integer likeType, @Param("contentIdList") List<Integer> contentIdList,@Param("userId") Integer userId);

}
