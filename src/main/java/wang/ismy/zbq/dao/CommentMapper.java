package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.dto.CommentCountDTO;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.entity.Comment;

import java.util.List;

public interface CommentMapper {

    int insertNew(Comment comment);

    List<Comment> selectCommentListByCommentTypeAndTopicIdBatch(
            @Param("commentType") Integer commentType,@Param("topicIdList") List<Integer> topicIdList);

    List<Comment> selectCommentByCommentTypeAndContentIdPaging(@Param("commentType") int code, @Param("topicId") Integer contentId, @Param("page")Page page);

    List<CommentCountDTO> selectCommentCountByCommentTypeAndTopicIdBatch(@Param("commentType") int code, @Param("idList") List<Integer> list);

}
