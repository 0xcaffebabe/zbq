package wang.ismy.zbq;

import org.junit.Test;

import org.yaml.snakeyaml.util.UriEncoder;
import wang.ismy.spider.Spider;
import wang.ismy.spider.request.Request;

public class SpiderTest {


    @Test
    public void test(){

        String kw = UriEncoder.encode("转笔探讨5");
        String url =
                "https://api.bilibili.com/x/web-interface/search/type?jsonp=jsonp&search_type=video&highlight=1&keyword="+kw+"&from_source=banner_search&page=2";

        Spider spider = new Spider();
        Request request = new Request();
        request.setUrl(url);
        request.header("Referer","https://search.bilibili.com/all?keyword="+kw+"&from_source=banner_search");


        spider.request(request,response -> {

            System.out.println(response.toTextResponse("utf8"));

        },false);

        spider.close();
    }
}
