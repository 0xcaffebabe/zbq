package wang.ismy.zbq.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author myx
 */
@Configuration
public class GsonConfig {

    @Bean
    public Gson gson(){
        return new Gson();
    }
}
