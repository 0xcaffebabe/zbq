package wang.ismy.zbq.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.vo.user.UserStateVO;

/**
 * @author my
 */
@Service
public class UserStateService {

    @Autowired
    private UserService userService;

    public UserStateVO selectByUserId(Integer userId){
        var user = userService.selectByPrimaryKey(userId);

        if (user == null) {
            ErrorUtils.error(R.USERNAME_NOT_EXIST);
        }

        UserStateVO vo = new UserStateVO();
        vo.setUserId(userId);
        vo.setNickName(user.getUserInfo().getNickName());
        vo.setProfile(user.getUserInfo().getProfile());
        return vo;
    }

}
