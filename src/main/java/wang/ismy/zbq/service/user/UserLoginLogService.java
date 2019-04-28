package wang.ismy.zbq.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.user.UserLoginLogMapper;
import wang.ismy.zbq.model.entity.user.UserLoginLog;
import wang.ismy.zbq.service.system.ExecuteService;

import java.util.List;

/**
 * @author my
 */
@Service
public class UserLoginLogService {

    @Autowired
    private UserLoginLogMapper userLoginLogMapper;

    @Autowired
    private ExecuteService executeService;

    @Autowired
    private UserService userService;

    public void addLog(UserLoginLog log){
        executeService.submit(()->userLoginLogMapper.insertNew(log));

    }

    public List<UserLoginLog> currentUserSelectTop10(){
        var currentUser = userService.getCurrentUser();

        return userLoginLogMapper.selectTop10ByUserId(currentUser.getUserId());
    }

    public Long calcCurrentUserLogDays() {

        var currentUser = userService.getCurrentUser();

        long days = userLoginLogMapper.countLogByUserId(currentUser.getUserId());

        return days;
    }
}
