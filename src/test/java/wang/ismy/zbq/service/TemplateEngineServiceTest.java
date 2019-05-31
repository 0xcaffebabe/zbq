package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.ismy.zbq.util.TimeUtils;

import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TemplateEngineServiceTest {

    @Autowired
    TemplateEngineService service;

    @Test
    public void testParse() {

        var str = service.parseStr(TemplateEngineService.COMMENT_TEMPLATE, Map.of(
                "time",TimeUtils.getStrTime(),
                "user","My、",
                "type","转笔内容",
                "content","给转笔新手的",
                "comment","测试评论123"
        ));

        System.out.println(str);
    }
}