package wang.ismy.zbq.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Likes {
    private Integer likeType;

    private Integer contentId;

    private Integer likeCount;

    private Boolean hasLike = false;

    private List<Like> likeList = new ArrayList<>();

}
