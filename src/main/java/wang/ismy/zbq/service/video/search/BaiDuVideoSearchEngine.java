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
public class BaiDuVideoSearchEngine implements VideoFetch{

    @Autowired
    private Spider spider = new Spider();
    @Override
    public List<Video> fetch(String kw, Page page) throws Exception {
        kw = URLEncoder.encode(kw,"utf8");
        Request request = new Request();
        request.url("https://www.baidu.com/sf/vsearch?pd=video&tn=vsearch&ie=utf-8&wrsv_spt=10&wd="+kw+"&async=1&pn=20");

        List<Video> videoList = new ArrayList<>();
        spider.request(request,response -> {
            response.toTextResponse("utf8")
                    .css(".video_list")
                    .forEach(e->{
                        var link = e.select(".video_list_title_small").attr("href");
                        var title = e.select(".video_list_title_small").text();
                        var img = e.select(".small_img_con").select("img").attr("src");
                        var source = e.select(".wetSource").text();
                        Video video = new Video();
                        video.setLink(link);
                        video.setTitle(title);
                        video.setSource(source);
                        video.setThumbnail(img);
                        videoList.add(video);

                    });

        },false);
        return videoList;
    }

    public static void main(String[] args) throws Exception {
        BaiDuVideoSearchEngine engine = new BaiDuVideoSearchEngine();
        engine.fetch("转笔探讨",new Page());
    }

    @Override
    public VideoSearchEngineEnum getEngine() {
        return VideoSearchEngineEnum.BAI_DU;
    }
}
