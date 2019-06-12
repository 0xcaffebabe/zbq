package wang.ismy.zbq.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.model.dto.course.LessonCommentDTO;
import wang.ismy.zbq.model.entity.user.User;

import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    private Integer commentId;

    private Integer commentType;

    private Integer topicId;

    private String content;

    private User fromUser;

    private User toUser;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static Comment convert(LessonCommentDTO dto,User fromUser){

        Comment c = new Comment();
        c.commentType = CommentTypeEnum.LESSON.getCode();
        c.topicId = dto.getLessonId();
        c.setContent(dto.getContent());
        c.fromUser = fromUser;

        if (dto.getToUser() != null){
            c.toUser = User.convert(dto.getToUser());
        }

        return c;
    }

}
