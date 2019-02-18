package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.entity.UserInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceTest {
    @Autowired
    UserInfoService userInfoService;


    @Test
    public void insertTest(){

        UserInfo userInfo = UserInfo.builder()
                            .nickName("ds my")
                .profile("http://baidu.com")
                .birthday(LocalDate.now())
                .penYear(1)
                .region("福建漳州")
                .gender(0)
                .description("i am my")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        userInfoService.insertUserInfo(userInfo);

        assertEquals(java.util.Optional.of(2),userInfo.getUserInfoId());
    }
}