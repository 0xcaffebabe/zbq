package wang.ismy.zbq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wang.ismy.spider.Spider;

/**
 * @author my
 */
@Configuration
public class SpiderConfig {

    @Bean
    public Spider spider(){
        return new Spider();
    }
}
