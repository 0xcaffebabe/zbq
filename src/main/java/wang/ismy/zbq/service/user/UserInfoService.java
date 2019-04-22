package wang.ismy.zbq.service.user;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.dao.user.UserInfoMapper;
import wang.ismy.zbq.dto.user.UserInfoDTO;
import wang.ismy.zbq.entity.user.User;
import wang.ismy.zbq.entity.user.UserAccount;
import wang.ismy.zbq.entity.user.UserInfo;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.util.ErrorUtils;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAccountService userAccountService;

    /**
     * 返回主键
     *
     * @param userInfo 用户信息实体
     * @return 受影响条数
     */
    public int insertUserInfo(UserInfo userInfo) {
        userInfoMapper.insertUserInfo(userInfo);
        return userInfo.getUserInfoId();
    }

    @MustLogin
    public int updateUserInfo(UserInfoDTO userInfoDTO) {
        User user = userService.getCurrentUser();
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDTO, userInfo);
        userInfo.setUserInfoId(user.getUserInfo().getUserInfoId());
        userInfoMapper.updateUserInfo(userInfo);

        if (userInfoMapper.updateUserInfo(userInfo) == 1) {
            userService.refreshCurrentUser();
            return 1;
        }
        return -1;

    }

    public UserInfoDTO getCurrentUserInfo() {
        User user = userService.getCurrentUser();
        if (user == null) {
            ErrorUtils.error(R.NOT_LOGIN);
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO();

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(user.getUserInfo().getUserInfoId());


        BeanUtils.copyProperties(userInfo, userInfoDTO);
        userInfoDTO.setEmail(getCurrentUserEmail(user));
        return userInfoDTO;
    }

    private String getCurrentUserEmail(User user) {
        UserAccount account = userAccountService.selectByAccountTypeAndUserId(UserAccountEnum.EMAIL,user.getUserId());
        String email = null;
        if (account != null){
            email = account.getAccountName();
        }
        return email;
    }

}
