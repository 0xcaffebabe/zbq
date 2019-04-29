package wang.ismy.zbq.service.video.search;

import wang.ismy.zbq.model.Video;
import wang.ismy.zbq.model.dto.Page;

import java.util.List;

/**
 * 视频拉取接口
 *
 * @author my
 */
public interface VideoFetch {


    /**
     * 各实现类实现视频拉取逻辑
     *
     * @param kw   搜索关键词
     * @param page 分页组件
     * @return 视频列表
     */
    List<Video> fetch(String kw, Page page);
}
