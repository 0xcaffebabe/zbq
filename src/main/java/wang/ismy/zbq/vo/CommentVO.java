package wang.ismy.zbq.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.entity.Comment;
import wang.ismy.zbq.entity.User;

import java.time.LocalDateTime;

@Data
public class CommentVO {

    private Integer commentId;

    private Integer commentType;

    private Integer topicId;

    private String content;

    private UserVO fromUser;

    private UserVO toUser;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static CommentVO convert(Comment comment){
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(comment,commentVO);
        commentVO.setFromUser(UserVO.builder().userId(comment.getFromUser().getUserId()).build());
        try{
            commentVO.setToUser(UserVO.builder().userId(comment.getToUser().getUserId()).build());
        }catch (NullPointerException e){

        }

        return commentVO;
    }
}
