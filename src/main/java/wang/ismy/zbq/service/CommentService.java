package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.CommentMapper;
import wang.ismy.zbq.entity.Comment;
import wang.ismy.zbq.enums.CommentTypeEnum;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> selectCommentByCommentTypeAndTopicId(CommentTypeEnum commentType,List<Integer> topicIds){

        if (topicIds.size() == 0){
            return List.of();
        }
        return commentMapper.selectCommentListByCommentTypeAndTopicIdBatch(commentType.getCode(),topicIds);
    }
}
