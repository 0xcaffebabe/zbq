package wang.ismy.zbq.video;

import java.net.MalformedURLException;
import java.net.URL;

public class YoukuVideoParser implements VideoParser {
    @Override
    public String getConcernHost() {
        return "v.youku.com";
    }

    @Override
    public String process(String url) {

        try {
            URL u = new URL(url);
            return "//player.youku.com/embed/"+u.getPath().replace("/v_show/id_","").replace(".html","");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }


}
