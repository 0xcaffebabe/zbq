package wang.ismy.zbq.service.system;

import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * @author my
 */
@Service
public class ExecuteService {

    private static ExecutorService executorService = Executors.newFixedThreadPool(6);

    public void submit(Runnable runnable){
        executorService.submit(runnable);
    }

}
