package wang.ismy.zbq.service.video.search;


import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.enums.VideoSearchEngineEnum;
import wang.ismy.zbq.model.Video;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.VideoSearchLog;
import wang.ismy.zbq.model.vo.HotKeywordVO;
import wang.ismy.zbq.model.vo.VideoSearchEngineVO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.CacheService;
import wang.ismy.zbq.service.VideoSearchLogService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
@Slf4j
public class VideoSearchService {

    @Autowired
    private List<VideoFetch> videoFetchList;

    private Map<VideoSearchEngineEnum, VideoFetch> searchEngineMapper;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private VideoSearchLogService videoSearchLogService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        searchEngineMapper = videoFetchList.stream()
                .collect(Collectors.toMap(VideoFetch::getEngine, e -> e));
    }

    public List<Video> search(VideoSearchEngineEnum engineEnum, String kw, Page page) {
        increaseKwCount(kw);
        recordLog(engineEnum,kw);
        String key = "videoSearch#" + engineEnum.toString() + "#" + kw + "#" + page.getPageNumber() + "," + page.getLength();

        long time = System.currentTimeMillis();

        List<Video> ret = cacheService.getFromJson(key, new TypeToken<List<Video>>() {}.getType());
        if (ret != null) {
            if (ret.size() != 0) {
                log.info("缓存命中：{},耗时:{}", key, System.currentTimeMillis() - time);
                return ret;
            }

        }
        if (engineEnum == VideoSearchEngineEnum.UNKNOWN) {
            ErrorUtils.error(R.UNKNOWN_SEARCH_ENGINE);
        }

        try {
            var engine = searchEngineMapper.get(engineEnum);

            if (engine == null) {
                ErrorUtils.error(engineEnum.getEngineName() + "搜索暂未实现");
            }
            var list = engine.fetch(kw, page);

            if (list == null || list.size() == 0) {

            } else {
                cacheService.put(key, list, 86400);
                log.info("缓存新增:{}", key);
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void recordLog(VideoSearchEngineEnum engineEnum, String kw) {

        VideoSearchLog log = new VideoSearchLog();
        log.setCreateTime(LocalDateTime.now());
        log.setKw(kw);
        log.setUser(userService.getCurrentUser());

        videoSearchLogService.push(log);
    }

    public void increaseKwCount(String kw) {
        cacheService.hIncrement("hot_kw", kw, 1L);
    }

    public List<HotKeywordVO> selectHotKw() {
        List<HotKeywordVO> list = new LinkedList<>();
        cacheService.getHashEntry("hot_kw")
                .forEach((k, v) -> {
                    HotKeywordVO vo = new HotKeywordVO();
                    vo.setKw(k);
                    vo.setHeat(v);
                    list.add(vo);
                });

        return list.stream()
                .sorted(Comparator.comparing(HotKeywordVO::getHeat).reversed())
                .limit(10)
                .collect(Collectors.toList());

    }

    public List<VideoSearchEngineVO> selectAllEngine() {

        var values = VideoSearchEngineEnum.values();
        List<VideoSearchEngineVO> voList = new ArrayList<>();
        for (var i : values) {
            if (i.getCode() == -1) {
                continue;
            }

            VideoSearchEngineVO vo = new VideoSearchEngineVO();
            vo.setCode(i.getCode());
            vo.setEngineName(i.getEngineName());

            voList.add(vo);

        }

        return voList;
    }

}
