package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.CommentMapper;
import wang.ismy.zbq.entity.Comment;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    public List<Comment> selectCommentByCommentTypeAndTopicId(CommentTypeEnum commentType, List<Integer> topicIds) {

        if (topicIds.size() == 0) {
            return List.of();
        }
        return commentMapper.selectCommentListByCommentTypeAndTopicIdBatch(commentType.getCode(), topicIds);
    }

    public int createNewCommentRecord(Comment comment) {

        if (comment.getToUser() != null ){
            if (userService.selectByPrimaryKey(comment.getToUser().getUserId()) == null){
                ErrorUtils.error(StringResources.TARGET_USER_NOT_EXIST);
            }
        }
        return commentMapper.insertNew(comment);
    }
}
