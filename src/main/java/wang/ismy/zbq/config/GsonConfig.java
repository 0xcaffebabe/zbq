package wang.ismy.zbq.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author my
 */
@Configuration
public class GsonConfig {

    @Bean
    public Gson gson(){
        return new Gson();
    }
}
