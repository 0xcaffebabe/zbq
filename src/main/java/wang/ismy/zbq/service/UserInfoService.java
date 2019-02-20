package wang.ismy.zbq.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.UserInfoMapper;
import wang.ismy.zbq.dto.UserDTO;
import wang.ismy.zbq.dto.UserInfoDTO;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.entity.UserInfo;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserService userService;

    /*
    *
    * 返回主键
    * */
    public int insertUserInfo(UserInfo userInfo){
        userInfoMapper.insertUserInfo(userInfo);
        return userInfo.getUserInfoId();
    }

    public int updateUserInfo(UserInfoDTO userInfoDTO){
        User user = userService.getCurrentUser();
        if (user == null){
            throw new RuntimeException("权限错误");
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDTO,userInfo);
        userInfo.setUserInfoId(user.getUserInfo().getUserInfoId());
        userInfoMapper.updateUserInfo(userInfo);

        if (userInfoMapper.updateUserInfo(userInfo) == 1){
            userService.refreshCurrentUser();
            return 1;
        }
        return -1;

    }

    public UserInfoDTO getCurrentUserInfo(){
        User user = userService.getCurrentUser();
        if (user == null){
            throw new RuntimeException("未登录");
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO();

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(user.getUserInfo().getUserInfoId());
        BeanUtils.copyProperties(userInfo,userInfoDTO);

        return userInfoDTO;
    }

}
