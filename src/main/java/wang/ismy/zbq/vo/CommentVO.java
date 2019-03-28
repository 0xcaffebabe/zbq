package wang.ismy.zbq.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.entity.Comment;
import wang.ismy.zbq.entity.User;

import java.time.LocalDateTime;


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

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getCommentType() {
        return commentType;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserVO getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserVO fromUser) {
        this.fromUser = fromUser;
    }

    public UserVO getToUser() {
        return toUser;
    }

    public void setToUser(UserVO toUser) {
        this.toUser = toUser;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
