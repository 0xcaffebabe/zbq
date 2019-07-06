package wang.ismy.zbq.service.video.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.spider.Spider;
import wang.ismy.spider.request.Request;
import wang.ismy.zbq.enums.VideoSearchEngineEnum;
import wang.ismy.zbq.model.Video;
import wang.ismy.zbq.model.dto.Page;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author my
 */
@Service
public class YouKuVideoSearchEngine implements VideoFetch {

    @Autowired
    private Spider spider;
    @Override
    public List<Video> fetch(String kw, Page page) throws Exception {

        kw = URLEncoder.encode(kw,"utf8");


        String url =
                "https://www.soku.com/nt/search/q_"+
                        kw+"_orderby_1_limitdate_0?spm=a2h0k.8191414.0.0&site=14&page="+page.getPageNumber();

        Request request = new Request();
        request.setUrl(url);

        List<Video> videoList = new ArrayList<>();
        spider.request(request,response -> {
           response.toTextResponse("utf8")
                   .css(".v")
                   .forEach(e->{
                       var i = e.select("img");
                       String title = i.attr("alt");

                       String img =  i.attr("src");

                       String link = e.select(".v-link a")
                               .attr("href");

                       Video video = new Video();
                       video.setTitle(title);
                       video.setThumbnail(img);
                       video.setLink(link);

                       video.setSource("优酷/土豆");
                       videoList.add(video);
                   });

        },false);

        return videoList;
    }

    public static void main(String[] args) throws Exception {
        YouKuVideoSearchEngine engine = new YouKuVideoSearchEngine();
        engine.fetch("转笔探讨",new Page());
    }

    @Override
    public VideoSearchEngineEnum getEngine() {
        return VideoSearchEngineEnum.YOU_KU;
    }
}
