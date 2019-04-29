package wang.ismy.zbq.service.video.parser;


/**
 * 实现该接口的类将能把指定视频网站的播放页解析成播放地址
 *
 * @author my
 */
public interface VideoParser {

    /**
     * 注册一个该解析器关心的域名，被解析的url属于这个域名，则会交给该处理器
     *
     * @return 域名
     */
    String getConcernHost();

    /**
     * 处理传入的视频地址
     *
     * @param url 欲处理的视频地址
     * @return 处理之后的播放地址
     */
    String process(String url);
}
