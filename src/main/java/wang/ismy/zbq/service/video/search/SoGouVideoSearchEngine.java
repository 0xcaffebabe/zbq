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
public class SoGouVideoSearchEngine implements VideoFetch {

    @Autowired
    private Spider spider;

    @Override
    public List<Video> fetch(String kw, Page page) throws Exception {

        kw = URLEncoder.encode(kw,"utf8");

        String url = "https://v.sogou.com/v?query="+kw+"&ie=utf8&tcc=1&tn=0&len=20&page=1";

        Request request = new Request();
        request.setUrl(url);

        List<Video> videoList = new ArrayList<>();
        spider.request(request,response -> {

            response.toTextResponse("GBK")
                    .css(".sort_lst_li")
                    .forEach(e->{
                        var a = e.select(".sort_lst_thumb");
                        String title = a
                                .attr("title");

                        String link = a
                                .attr("uigs")
                                .split("\\|")[2];

                        String img = a.select("img")
                                .attr("src");
                        String source = e.select(".sort_lst_txt_rgt").text();

                        Video video = new Video();
                        video.setTitle(title);
                        video.setLink(link);
                        video.setThumbnail(img);
                        video.setSource(source);
                        videoList.add(video);

                    });
        },false);


        return videoList;
    }

    public static void main(String[] args) throws Exception {
        SoGouVideoSearchEngine engine = new SoGouVideoSearchEngine();
        engine.fetch("转笔探讨",new Page());
    }

    @Override
    public VideoSearchEngineEnum getEngine() {
        return VideoSearchEngineEnum.SO_GOU;
    }
}
