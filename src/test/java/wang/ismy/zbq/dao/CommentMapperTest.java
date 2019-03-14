package wang.ismy.zbq.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.entity.Comment;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.service.UserService;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentMapperTest {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserService userService;

    @Test
    public void testInsert(){
        Comment comment = new Comment();
        comment.setCommentType(0);
        comment.setContent("测试评论回复");
        comment.setFromUser(userService.selectByPrimaryKey(2));
        comment.setToUser(User.builder().userId(1).build());
        comment.setTopicId(8);
        assertEquals(1,commentMapper.insertNew(comment));
    }


    @Test
    public void testSelect(){


    }
}