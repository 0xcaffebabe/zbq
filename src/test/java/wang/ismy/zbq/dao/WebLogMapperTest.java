package wang.ismy.zbq.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.entity.WebLog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WebLogMapperTest {

    @Autowired
    WebLogMapper webLogMapper;


    @Test
    public void testInsert(){
        WebLog webLog = new WebLog();
        webLog.setIp("127.0.0.1");
        webLog.setCreateTime(LocalDateTime.now());
        webLog.setTimeConsuming(25);
        webLog.setUa("mozllia");
        webLog.setUrl("/main");

        assertEquals(1,webLogMapper.insertNew(webLog));
    }


    @Test
    public void testInsertBatch(){
        List<WebLog> webLogList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            WebLog webLog = new WebLog();
            webLog.setIp("127.0.0.1."+i);
            webLog.setCreateTime(LocalDateTime.now());
            webLog.setTimeConsuming(25);
            webLog.setUa("mozllia");
            webLog.setUrl("/main");
            webLogList.add(webLog);
        }
        assertEquals(10,webLogMapper.insertNewBatch(webLogList));
    }
}