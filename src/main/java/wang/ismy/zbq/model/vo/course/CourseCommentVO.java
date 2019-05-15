package wang.ismy.zbq.model.vo.course;

import lombok.Data;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.model.vo.user.UserVO;

import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class CourseCommentVO {

    private Integer commentId;

    private UserVO fromUser;

    private UserVO toUser;

    private LessonListVO lesson;

    private String content;

    private LocalDateTime createTime;

    public static CourseCommentVO convert(Comment comment){
        CourseCommentVO vo = new CourseCommentVO();
        vo.commentId = comment.getCommentId();

        vo.fromUser = UserVO.convert(comment.getFromUser());

        if (comment.getToUser() != null){
            vo.toUser = UserVO.convert(comment.getToUser());
        }

        vo.lesson = new LessonListVO(comment.getTopicId());

        vo.content = comment.getContent();
        vo.createTime = comment.getCreateTime();
        return vo;
    }
}
