package wang.ismy.zbq.config;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wang.ismy.zbq.service.video.search.VideoFetch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author my
 */
@Configuration
public class VideoSearchEngineConfig {


    @Bean
    public List<VideoFetch> a(ApplicationContext context){
        String[] names = context.getBeanNamesForType(VideoFetch.class);

        List<VideoFetch> videoFetches = new ArrayList<>();
        for (var i : names){
            videoFetches.add(context.getBean(i,VideoFetch.class));
        }

        return videoFetches;
    }
}
