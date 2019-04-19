package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.UserLoginLogMapper;
import wang.ismy.zbq.entity.UserLoginLog;

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
}
