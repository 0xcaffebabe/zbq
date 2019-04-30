package wang.ismy.zbq.service.video.search;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.spider.Spider;
import wang.ismy.spider.request.Request;
import wang.ismy.zbq.enums.VideoSearchEngineEnum;
import wang.ismy.zbq.model.Video;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.vo.HotKWVO;
import wang.ismy.zbq.model.vo.VideoSearchEngineVO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.CacheService;
import wang.ismy.zbq.util.ErrorUtils;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    private Map<VideoSearchEngineEnum,VideoFetch> searchEngineMapper;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private Gson gson;

    @PostConstruct
    public void init(){
        searchEngineMapper = videoFetchList.stream()
                .collect(Collectors.toMap(VideoFetch::getEngine,e-> e));
    }

    public List<Video> search(VideoSearchEngineEnum engineEnum, String kw, Page page){
        increaseKwCount(kw);
        String key = "videoSearch#"+engineEnum.toString()+"#"+kw+"#"+page.getPageNumber()+","+page.getLength();

        long time = System.currentTimeMillis();
        String str = cacheService.get(key);
        if (str!=null){
            str = str.trim();
        }

        List ret = gson.fromJson(str, new TypeToken<List<Video>>() {}.getType());
        if (ret != null){
            if (ret.size() != 0){
                log.info("缓存命中：{},耗时:{}",key, System.currentTimeMillis()-time);
                return ret;
            }

        }
        if (engineEnum == VideoSearchEngineEnum.UNKNOWN){
            ErrorUtils.error(R.UNKNOWN_SEARCH_ENGINE);
        }

        try {
            var engine = searchEngineMapper.get(engineEnum);

            if (engine == null){
                ErrorUtils.error(engineEnum.getEngineName()+"搜索暂未实现");
            }
            var list = engine.fetch(kw,page);

            if (list == null || list.size() == 0){

            }else{
                cacheService.put(key,list,1000);
                log.info("缓存新增:{}",key);
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void increaseKwCount(String kw){
        cacheService.hIncreasement("hot_kw",kw,1L);
    }

    public List<HotKWVO> selectHotKw(){
        List<HotKWVO> list = new LinkedList<>();
        cacheService.getHashEntry("hot_kw")
                .forEach((k,v)->{
                    HotKWVO vo = new HotKWVO();
                    vo.setKw(k);
                    vo.setHeat(v);
                    list.add(vo);
                });
        list.sort(Comparator.comparing(HotKWVO::getHeat).reversed());
        return list;
    }

    public List<VideoSearchEngineVO> selectAllEngine(){

        var values = VideoSearchEngineEnum.values();

        List<VideoSearchEngineVO> voList = new ArrayList<>();
        for (var i :values){
            if (i.getCode() == -1){
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
