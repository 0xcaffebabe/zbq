package wang.ismy.zbq.model.dto;

import lombok.Data;


/**
 * @author my
 */
@Data
public class RequestLimitDTO {

    private Integer requestCount;

    private Long lastRequestTime;

    public synchronized void increaseCount(){
        requestCount++;
    }

    public synchronized void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
    }
}
