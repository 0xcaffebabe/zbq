package wang.ismy.zbq.service.video;


/*
* 实现该接口的类将能把指定视频网站的播放页解析成播放地址
*/
public interface VideoParser {

    /*
    * 注册一个该解析器关心的域名，被解析的url属于这个域名，则会交给该处理器
    */
    String getConcernHost();

    String process(String url);
}
