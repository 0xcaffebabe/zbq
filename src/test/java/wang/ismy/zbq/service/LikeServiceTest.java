package wang.ismy.zbq.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import wang.ismy.zbq.enums.LikeTypeEnum;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LikeServiceTest {

    @Autowired
    LikeService likeService;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @Test
    public void testCount(){

        System.out.println(likeService.countLike(2));
    }


    @Test
    public void testHasLike(){
        var map = likeService.
                hasLikeBatch(LikeTypeEnum.CONTENT, List.of( 14 , 13 , 12 , 2 , 3),60);

        System.out.println(map);
    }


    @Test
    public void testTemplate() throws IOException, TemplateException {
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("email/likeInform.html");

        Map<String,Object> model = new HashMap<>();
        model.put("id",123);
        String out= FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
        log.error(out);

    }
}