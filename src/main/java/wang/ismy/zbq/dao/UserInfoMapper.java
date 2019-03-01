package wang.ismy.zbq.dao;

import wang.ismy.zbq.entity.UserInfo;

import java.util.List;

public interface UserInfoMapper {

    int insertUserInfo(UserInfo userInfo);

    int updateUserInfo(UserInfo userInfo);

    UserInfo selectByPrimaryKey(Integer key);

}
