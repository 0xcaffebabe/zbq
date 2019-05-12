package wang.ismy.zbq.service.system;

import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * @author my
 */
@Service
public class ExecuteService {

    private static ExecutorService executorService = Executors.newFixedThreadPool(6);

    public Future<?> submit(Runnable runnable){
        return executorService.submit(runnable);
    }

    public <T> Future<T>  submit(Callable<T> callable){

        return executorService.submit(callable);
    }

}
