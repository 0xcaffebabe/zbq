package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.WebLogMapper;
import wang.ismy.zbq.entity.WebLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author my
 */
@Service
public class LogService {
    @Autowired
    private WebLogMapper webLogMapper;

    private static final int WEB_LOG_QUEUE_MAX_SIZE = 10;
    private Queue<WebLog> webLogQueue = new ConcurrentLinkedQueue<>();

    @Autowired
    private ExecuteService executeService;

    public void pushWebLog(WebLog webLog){
        executeService.submit(()->{
            webLogQueue.add(webLog);
            if (webLogQueue.size() >= WEB_LOG_QUEUE_MAX_SIZE){
                List<WebLog> webLogList = new ArrayList<>();
                for (int i = 0; i < WEB_LOG_QUEUE_MAX_SIZE; i++) {
                    webLogList.add(webLogQueue.poll());
                }
                webLogMapper.insertNewBatch(webLogList);
            }
        });
    }
}
