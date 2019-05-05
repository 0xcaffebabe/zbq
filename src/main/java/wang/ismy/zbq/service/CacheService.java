package wang.ismy.zbq.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.service.system.ExecuteService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author my
 */
@Service
@Slf4j
public class CacheService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ExecuteService executeService;

    @Autowired
    private Gson gson;

    public void put(String key,Object value){

        executeService.submit(()-> saveInRedis(key, value));

    }

    public void put(String key,Object value,long expired){

        executeService.submit(()->{
            saveInRedis(key, value);
            redisTemplate.expire(key,expired,TimeUnit.SECONDS);
        });

    }

    private void saveInRedis(String key, Object value) {
        if (value instanceof String){
            redisTemplate.opsForValue().set(key,value.toString());
            log.info("设置字符串缓存:{}",key);
        }else{
            redisTemplate.opsForValue().set(key,gson.toJson(value));
            log.info("设置对象缓存:{}",key);
        }
    }

    public String get(String key){

        return redisTemplate.opsForValue().get(key);

    }

    public Object getFromJson(String key){
        return null;
    }

    public void hIncrement(String hash, String key, Long step){
        executeService.submit(()->{
            redisTemplate.opsForHash().increment(hash,key,step);
        });
    }

    public Map<String,Long> getHashEntry(String hash){
        var map = redisTemplate.opsForHash().entries(hash);

        Map<String,Long> ret = new HashMap<>();
        for (var k : map.keySet()){
            ret.put(k.toString(),Long.valueOf(map.get(k).toString()));

        }

        return ret;
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }
}
