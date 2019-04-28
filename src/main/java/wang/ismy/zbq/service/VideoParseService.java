package wang.ismy.zbq.service;

import org.springframework.stereotype.Service;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.service.video.BiliBiliVideoParser;
import wang.ismy.zbq.service.video.VideoParser;
import wang.ismy.zbq.service.video.YoukuVideoParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/*
* 解析视频播放地址
*/
@Service
public class VideoParseService {

    Map<String,VideoParser> parserMap = new HashMap<>();

    public VideoParseService() {
        BiliBiliVideoParser biliBiliVideoParser = new BiliBiliVideoParser();
        parserMap.put(biliBiliVideoParser.getConcernHost(),biliBiliVideoParser);
        YoukuVideoParser youkuVideoParser = new YoukuVideoParser();
        parserMap.put(youkuVideoParser.getConcernHost(),youkuVideoParser);
    }

    public String parse(String url){
        url = url.replaceAll("\"","");
        try {
            URL u = new URL(url);
            VideoParser parser = parserMap.get(u.getHost());
            if (parser == null){
                ErrorUtils.error(R.VIDEO_PARSER_NOT_FOUND);
            }
            return parser.process(url);
        } catch (MalformedURLException e) {
            ErrorUtils.error(R.VIDEO_PARSE_FAIL);
        }
        return null;


    }

}
