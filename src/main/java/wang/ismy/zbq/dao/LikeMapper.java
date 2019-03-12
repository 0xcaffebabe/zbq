package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.entity.Like;
import wang.ismy.zbq.enums.LikeTypeEnum;

import java.util.List;

public interface LikeMapper {

    int insertNew(Like like);

    List<Like> selectLikeListByLikeTypeAndContentId(@Param("likeType") int likeType, @Param("contentId") Integer contentId);

    List<Like> selectLikeListByLikeTypeAndContentIdBatch(@Param("likeType") Integer likeType, @Param("contentIdList") List<Integer> contentIdList);

}
