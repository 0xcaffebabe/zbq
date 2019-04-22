package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.user.LoginACLMapper;

@Service
public class LoginACLService {

    @Autowired
    private LoginACLMapper loginACLMapper;

    public int insertNew(Integer userId){
        return loginACLMapper.insert(userId);
    }

    public boolean canLogin(Integer userId){
        Boolean ret = loginACLMapper.selectLoginStateByUserId(userId);

        if (ret == null){
            return true;
        }

        return ret;
    }
}
