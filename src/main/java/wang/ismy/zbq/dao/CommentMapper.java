package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.entity.Comment;

import java.util.List;

public interface CommentMapper {

    int insertNew(Comment comment);

    List<Comment> selectCommentListByCommentTypeAndTopicIdBatch(
            @Param("commentType") Integer commentType,@Param("topicIdList") List<Integer> topicIdList);
}
