package wang.ismy.zbq.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.entity.Comment;

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
        UserVO from = new UserVO();
        from.setUserId(comment.getFromUser().getUserId());
        commentVO.setFromUser(from);
        try{
            UserVO to= new UserVO();
            to.setUserId(comment.getToUser().getUserId());
            commentVO.setToUser(to);
        }catch (NullPointerException e){

        }

        return commentVO;
    }


}
