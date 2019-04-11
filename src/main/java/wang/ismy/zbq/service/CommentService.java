package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.CommentMapper;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.entity.Comment;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.util.ErrorUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    public List<Comment> selectCommentByCommentTypeAndTopicId(CommentTypeEnum commentType,
                                                              List<Integer> topicIds) {

        if (topicIds.size() == 0) {
            return List.of();
        }
        return commentMapper.selectCommentListByCommentTypeAndTopicIdBatch(commentType.getCode(), topicIds);
    }

    public List<Comment> selectCommentByCommentTypeAndContentIdPaging(CommentTypeEnum commentType,
                                                                      Integer contentId,
                                                                      Page p){
        return commentMapper.selectCommentByCommentTypeAndContentIdPaging(commentType.getCode(),contentId,p);
    }

    public Map<Integer,Long> selectCommentCountByCommentTypeAndTopicIdBatch(CommentTypeEnum commentType,List<Integer> idList){
        var list = commentMapper.selectCommentCountByCommentTypeAndTopicIdBatch(commentType.getCode(),idList);

        Map<Integer,Long> ret= new HashMap<>();
        for (var i : list){
            ret.put(i.getContentId(),i.getCount());
        }

        return ret;
    }
    public int createNewCommentRecord(Comment comment) {

        if (comment.getToUser() != null ){
            if (userService.selectByPrimaryKey(comment.getToUser().getUserId()) == null){
                ErrorUtils.error(R.TARGET_USER_NOT_EXIST);
            }
        }
        return commentMapper.insertNew(comment);
    }
}
