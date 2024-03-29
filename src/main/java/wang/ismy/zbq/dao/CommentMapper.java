package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.dto.CommentCountDTO;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.Comment;

import java.util.List;






/**
 * @author my
 */
public interface CommentMapper {

    /**
     * 向数据库中插入一条新品类
     *
     * @param comment 评论实体
     * @return 数据库中受影响的行数
     */
    int insertNew(Comment comment);

    /**
     * 根据CommentType和TopicIdList查询出评论列表
     *
     * @param commentType 评论类型
     * @param topicIdList 主题ID列表
     * @return 查询出的评论列表
     */
    List<Comment> selectCommentListByCommentTypeAndTopicIdBatch(
            @Param("commentType") Integer commentType, @Param("topicIdList") List<Integer> topicIdList);

    /**
     * 根据commentType和contentId分页地查询出评论列表
     *
     * @param commentType 评论类型
     * @param contentId 内容ID
     * @param page 分页
     * @return 查询的所有列表
     */
    List<Comment> selectCommentByCommentTypeAndContentIdPaging(@Param("commentType") int commentType, @Param("topicId") Integer contentId, @Param("page") Page page);

    List<CommentCountDTO> selectCommentCountByCommentTypeAndTopicIdBatch(@Param("commentType") int code, @Param("idList") List<Integer> list);

    /**
     * 根据commentType和topicIdList分页地查询出评论列表
     *
     * @param type 评论类型
     * @param topicIds 内容ID列表
     * @param page 分页组件
     * @return 评论列表
     */
    List<Comment> selectCommentListByCommentTypeAndTopicIdBatchPaging(@Param("type") int type,
                                                                      @Param("list") List<Integer> topicIds,
                                                                      @Param("page") Page page);

    /**
    * 分页查询用户评论列表（根据时间降序）
    * @param userId 用户ID
     * @param page 分页组件
     * @return 评论列表
    */
    List<Comment> selectCommentByUserPaging(@Param("user") Integer userId, @Param("page") Page page);
}
