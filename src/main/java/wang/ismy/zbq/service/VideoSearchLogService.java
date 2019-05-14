package wang.ismy.zbq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.VideoSearchLogMapper;
import wang.ismy.zbq.model.entity.VideoSearchLog;
import wang.ismy.zbq.service.system.ExecuteService;

/**
 * @author my
 */
@Service
@Slf4j
public class VideoSearchLogService {

    @Autowired
    private VideoSearchLogMapper videoSearchLogMapper;

    @Autowired
    private ExecuteService executeService;

    public void push(VideoSearchLog searchLog){
        executeService.submit(()->{
            if (videoSearchLogMapper.insertNew(searchLog) != 1){
                log.info("新增视频搜索记录失败:{}",searchLog);
            }
        });
    }
}
