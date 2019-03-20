package wang.ismy.zbq.video;

import java.net.MalformedURLException;
import java.net.URL;

public class BiliBiliVideoParser implements VideoParser {

    @Override
    public String getConcernHost() {
        return "www.bilibili.com";
    }

    @Override
    public String process(String url) {

        try {
            URL u = new URL(url);
            return "//player.bilibili.com/player.html?aid="+u.getPath().replaceAll("/video/av","");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        BiliBiliVideoParser parser = new BiliBiliVideoParser();
        System.out.println(parser.process("https://www.bilibili.com/video/av42856333"));
    }
}
