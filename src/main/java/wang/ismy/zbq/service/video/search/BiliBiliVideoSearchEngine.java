package wang.ismy.zbq.service.video.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.spider.Spider;
import wang.ismy.spider.request.Request;
import wang.ismy.zbq.enums.VideoSearchEngineEnum;
import wang.ismy.zbq.model.Video;
import wang.ismy.zbq.model.dto.Page;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author my
 */
@Service
public class BiliBiliVideoSearchEngine implements VideoFetch {

    @Autowired
    private Spider spider;

    @Override
    public List<Video> fetch(String kw, Page page) throws UnsupportedEncodingException {
        kw = URLEncoder.encode(kw,"utf8");
        String url =
                "https://api.bilibili.com/x/web-interface/search/type?jsonp=jsonp&search_type=video&highlight=1&keyword="+
                        kw+"&from_source=banner_search&page=1";

        Request request = new Request();
        request.setUrl(url);
        request.header("Referer","https://search.bilibili.com/all?keyword="+kw+"&from_source=banner_search");


        List<Video> videoList = new ArrayList<>();
        spider.request(request,response -> {

            JsonObject jsonObject = new JsonParser().parse(response.toText("utf8")).getAsJsonObject();

            JsonArray jsonArray = jsonObject.get("data")
                    .getAsJsonObject()
                    .get("result")
                    .getAsJsonArray();


            for (var i : jsonArray){
                var obj = i.getAsJsonObject();
                Video video = new Video();
                video.setTitle(obj.get("title").getAsString());
                video.setThumbnail(obj.get("pic").getAsString());
                video.setLink(obj.get("arcurl").getAsString());
                video.setSource("B站");
                videoList.add(video);
            }

        },false);


        return videoList;
    }

    @Override
    public VideoSearchEngineEnum getEngine() {
        return VideoSearchEngineEnum.BILI_BILI;
    }
}
