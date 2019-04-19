package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.service.system.OSSService;

import java.io.FileInputStream;
import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OSSServiceTest {

    @Autowired
    OSSService ossService;

    @Test
    public void uploadTest() throws IOException {

        String uri = "C:\\Users\\chenj\\Desktop\\序列 01.00_00_35_27.静止011.jpg";

        FileInputStream inputStream = new FileInputStream(uri);


    }
}