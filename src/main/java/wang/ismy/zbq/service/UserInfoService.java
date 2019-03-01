package wang.ismy.zbq.service;

import com.mysql.cj.core.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.dao.UserInfoMapper;
import wang.ismy.zbq.dto.UserDTO;
import wang.ismy.zbq.dto.UserInfoDTO;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.entity.UserInfo;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserService userService;

    /*
    * 返回主键
    * */
    public int insertUserInfo(UserInfo userInfo){
        userInfoMapper.insertUserInfo(userInfo);
        return userInfo.getUserInfoId();
    }

    @MustLogin
    public int updateUserInfo(UserInfoDTO userInfoDTO){
        User user = userService.getCurrentUser();
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
            ErrorUtils.error(StringResources.NOT_LOGIN);
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO();

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(user.getUserInfo().getUserInfoId());
        BeanUtils.copyProperties(userInfo,userInfoDTO);

        return userInfoDTO;
    }

}
