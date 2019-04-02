package wang.ismy.zbq.dto;

import lombok.Data;


public class ContentLikeDTO {

    private Integer id;

    private Long count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}

