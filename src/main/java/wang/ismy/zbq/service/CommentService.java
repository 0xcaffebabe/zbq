package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.CommentMapper;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.model.vo.CommentVO;
import wang.ismy.zbq.model.vo.user.UserVO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    /**
     * 查询评论列表
     *
     * @param commentType 评论类型
     * @param topicIds    主题ID列表
     * @return 评论列表
     */
    public List<Comment> selectComments(CommentTypeEnum commentType,
                                        List<Integer> topicIds) {

        if (topicIds.size() == 0) {
            return List.of();
        }
        return commentMapper.selectCommentListByCommentTypeAndTopicIdBatch(commentType.getCode(), topicIds);
    }

    /**
     * 取出评论列表
     *
     * @param commentType 评论类型
     * @param contentId   内容ID
     * @param page        分页组件
     * @return 评论列表
     */
    public List<Comment> selectComments(CommentTypeEnum commentType,
                                        Integer contentId,
                                        Page page) {
        return commentMapper.selectCommentByCommentTypeAndContentIdPaging(commentType.getCode(), contentId, page);
    }

    public List<CommentVO> selectCommentVOList(CommentTypeEnum commentType,
                                               Integer contentId,
                                               Page page) {
        var commentList = selectComments(commentType, contentId, page);

        var commentVOList = commentList.stream()
                .map(CommentVO::convert)
                .collect(Collectors.toList());

        List<Integer> userIdList = new ArrayList<>();

        for (var i : commentVOList) {
            if (i.getToUser() != null && !userIdList.contains(i.getToUser().getUserId())) {
                userIdList.add(i.getToUser().getUserId());

            }

            if (!userIdList.contains(i.getFromUser().getUserId())) {
                userIdList.add(i.getFromUser().getUserId());
            }


        }

        var userList = userService.selectByUserIdBatch(userIdList);

        Map<Integer, UserVO> userVOMap = new HashMap<>();
        for (var i : userList) {
            userVOMap.put(i.getUserId(), UserVO.convert(i));
        }

        for (var i : commentVOList) {

            i.setFromUser(userVOMap.get(i.getFromUser().getUserId()));

            if (i.getToUser() != null) {
                i.setToUser(userVOMap.get(i.getToUser().getUserId()));
            }
        }

        return commentVOList;

    }

    /**
     * 查询主题评论数量
     *
     * @param commentType 评论类型
     * @param idList      主题ID列表
     * @return 主题ID映射评论数量哈希表
     */
    public Map<Integer, Long> selectCommentCount(CommentTypeEnum commentType, List<Integer> idList) {

        if (idList == null || idList.size() == 0) {
            return Map.of();
        }
        var list = commentMapper.selectCommentCountByCommentTypeAndTopicIdBatch(commentType.getCode(), idList);

        Map<Integer, Long> ret = new HashMap<>();
        for (var i : list) {
            ret.put(i.getContentId(), i.getCount());
        }

        return ret;
    }

    public int createNewCommentRecord(Comment comment) {

        if (comment.getToUser() != null) {
            if (userService.selectByPrimaryKey(comment.getToUser().getUserId()) == null) {
                ErrorUtils.error(R.TARGET_USER_NOT_EXIST);
            }
        }
        return commentMapper.insertNew(comment);
    }
}
