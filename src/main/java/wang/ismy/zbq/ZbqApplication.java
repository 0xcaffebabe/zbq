package wang.ismy.zbq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "wang.ismy.zbq")
@MapperScan("wang.ismy.zbq.dao")
public class ZbqApplication {


    public static void main(String[] args) {
        SpringApplication.run(ZbqApplication.class, args);
    }

}

