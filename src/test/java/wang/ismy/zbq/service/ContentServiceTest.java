package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.model.dto.content.ContentDTO;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.service.user.UserService;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentServiceTest {

    @Autowired
    private ContentService contentService;

    @Autowired
    private UserService userService;

    @Test
    public void testInsert(){
        userService.setTestUser(userService.selectByPrimaryKey(1));

        for (int i =0;i<10;i++){
            ContentDTO contentDTO = new ContentDTO();
            contentDTO.setTitle("转笔的转后护理"+i);
            contentDTO.setContent("假装这里有内容");
            contentDTO.setTags("转笔;ivan");
            contentService.currentUserPublish(contentDTO);
        }

    }


    @Test
    public void testSelect(){
        Page page = new Page(1,5);
        var list = contentService.selectContentListPaging(page);

        assertEquals(5,list.size());

        assertEquals(Long.valueOf(1),list.get(0).getLikeCount());
    }


    @Test
    public void testSingle(){
        var content = contentService.selectByPrimaryKey(1);

        assertEquals("转笔的转后护理",content.getTitle());

        assertEquals("root",content.getUser().getNickName());
    }
}