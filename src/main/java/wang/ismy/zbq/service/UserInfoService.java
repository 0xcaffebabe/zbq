package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.UserInfoMapper;
import wang.ismy.zbq.entity.UserInfo;

@Service
public class UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    /*
    *
    * 返回主键
    * */
    public int insertUserInfo(UserInfo userInfo){
        userInfoMapper.insertUserInfo(userInfo);
        return userInfo.getUserInfoId();
    }
}
