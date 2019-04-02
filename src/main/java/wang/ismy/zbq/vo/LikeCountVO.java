package wang.ismy.zbq.vo;

public class LikeCountVO {

    private Long total;

    private Long stateLike;

    private Long contentLike;

    public Long getTotal() {
        return stateLike+contentLike;
    }


    public Long getStateLike() {
        return stateLike;
    }

    public void setStateLike(Long stateLike) {
        this.stateLike = stateLike;
    }

    public Long getContentLike() {
        return contentLike;
    }

    public void setContentLike(Long contentLike) {
        this.contentLike = contentLike;
    }
}
