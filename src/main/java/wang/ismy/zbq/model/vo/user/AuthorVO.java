package wang.ismy.zbq.model.vo.user;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.model.entity.user.User;

/**
 * @author my
 */
@Data
public class AuthorVO {

    private Integer userId;

    private String nickName;

    private String profile;

    private String region;

    private Integer penYear;

    private Boolean attention;


    public static AuthorVO convert(User user) {
        if (user == null) {
            return null;
        }
        AuthorVO authorVO = new AuthorVO();
        authorVO.setUserId(user.getUserId());

        if (user.getUserInfo() != null){
            authorVO.setNickName(user.getUserInfo().getNickName());
            authorVO.setProfile(user.getUserInfo().getProfile());
            authorVO.setRegion(user.getUserInfo().getRegion());
            authorVO.setPenYear(user.getUserInfo().getPenYear());
        }

        return authorVO;
    }

    public static AuthorVO of(Integer userId){
        AuthorVO vo = new AuthorVO();
        vo.userId = userId;
        return vo;
    }
}
