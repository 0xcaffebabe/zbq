package wang.ismy.zbq.dao;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.service.user.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentMapperTest {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserService userService;


}