package wang.ismy.zbq.dao.user;

import wang.ismy.zbq.entity.user.UserInfo;

public interface UserInfoMapper {

    int insertUserInfo(UserInfo userInfo);

    int updateUserInfo(UserInfo userInfo);

    UserInfo selectByPrimaryKey(Integer key);

}
