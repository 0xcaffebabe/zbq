package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.dao.UserMapper;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.dto.RegisterDTO;
import wang.ismy.zbq.entity.Permission;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.entity.UserInfo;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private LoginACLService loginACLService;

    @Transactional
    public int createNewUser(RegisterDTO dto) {


        User user = generateUser(dto);

        if (userMapper.selectByUsername(dto.getUsername()) != null) {
            ErrorUtils.error(StringResources.USERNAME_OCCUPY);
        }
        UserInfo userInfo = getDefaultUserInfo();
        int userInfoId = userInfoService.insertUserInfo(userInfo);

        if (userInfoId < 1) {
            ErrorUtils.error(StringResources.UNKNOWN_ERROR);
        }

        userInfo.setUserInfoId(userInfoId);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setUserInfo(userInfo);


        Permission permission = getDefaultPermission();
        permissionService.insertPermission(permission);
        user.setPermission(permission);

        int ret = userMapper.insert(user);

        // 插入一条登录权限
        if (loginACLService.insertNew(user.getUserId()) != 1){
            ErrorUtils.error(StringResources.UNKNOWN_ERROR);
        }


        // 如果登录成功直接以当前用户登录
        if (ret == 1) {
            User currentUser = userMapper.selectByUsername(dto.getUsername());
            setCurrentUser(currentUser);
        }
        return ret;
    }

    public void login(String username, String password) {

        User user = userMapper.selectByUsername(username);
        if (user == null) {
            ErrorUtils.error(StringResources.LOGIN_FAIL);
        }

        if (!loginACLService.canLogin(user.getUserId())){
            ErrorUtils.error(StringResources.ACCOUNT_DISABLE);
        }

        if (user.getPassword().equals(password)) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            request.getSession().setAttribute("user", user);

        } else {
            ErrorUtils.error(StringResources.LOGIN_FAIL);
        }
    }

    @MustLogin
    public User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            return null;
        }

        if (user instanceof User) {
            return (User) user;
        }

        return null;
    }

    public List<User> selectUserByUsernamePaging(String nickname, Page page){
        return userMapper.selectByNickNamePaging(nickname,page);
    }

    public User selectByPrimaryKey(Integer userId){

        return userMapper.selectByPrimaryKey(userId);
    }

    public List<User> selectByUserIdBatch(List<Integer> list){
        return userMapper.selectByUserIdBatch(list);
    }

    public boolean hasLogin() {
        return getCurrentUser() != null;
    }

    public void refreshCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        User user = userMapper.selectByUsername(getCurrentUser().getUsername());

        request.getSession().setAttribute("user", user);
    }

    private User generateUser(RegisterDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword().toUpperCase()).build();
    }

    private Permission getDefaultPermission() {
        Permission permission = new Permission();
        permission.setContentPublish("N");
        return permission;
    }

    private void setCurrentUser(User user){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute("user", user);
    }

    private UserInfo getDefaultUserInfo() {
        return UserInfo.builder()
                .nickName("佚名")
                .profile("img/anonymous.jpg")
                .birthday(LocalDate.now())
                .penYear(1)
                .region("中国")
                .gender(0)
                .description("这个人很懒，没有留下介绍")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }
}
