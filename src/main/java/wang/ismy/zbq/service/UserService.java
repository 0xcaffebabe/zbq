package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.Permission;
import wang.ismy.zbq.dao.UserMapper;
import wang.ismy.zbq.dto.MessageDTO;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.dto.RegisterDTO;
import wang.ismy.zbq.entity.UserPermission;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.entity.UserInfo;
import wang.ismy.zbq.enums.PermissionEnum;
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

    @Autowired
    private FriendService friendService;

    @Autowired
    private MessageService messageService;

    public void setTestUser(User testUser) {
        this.testUser = testUser;
    }

    private User testUser;

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


        UserPermission userPermission = getDefaultPermission();
        permissionService.insertPermission(userPermission);
        user.setUserPermission(userPermission);

        int ret = userMapper.insert(user);

        // 插入一条登录权限
        if (loginACLService.insertNew(user.getUserId()) != 1) {
            ErrorUtils.error(StringResources.UNKNOWN_ERROR);
        }

        // 创建一条该用户与系统账号的好友关系
        friendService.insertNewRelation(user.getUserId(), 0);
        friendService.insertNewRelation(0, user.getUserId());

        // 以小助手的身份发送一条消息给该用户
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent("欢迎来到转笔圈，有不懂的可以问我");
        messageDTO.setTo(user.getUserId());
        User t = new User();
        t.setUserId(0);
        messageService.sendMessage(t, messageDTO);
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

        if (!loginACLService.canLogin(user.getUserId())) {
            ErrorUtils.error(StringResources.ACCOUNT_DISABLE);
        }

        if (user.getPassword().equals(password)) {
            setCurrentUser(user);


        } else {
            ErrorUtils.error(StringResources.LOGIN_FAIL);
        }
    }

    @MustLogin
    public User getCurrentUser() {

        if (testUser != null) return testUser;
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

    public List<User> selectUserByUsernamePaging(String nickname, Page page) {
        return userMapper.selectByNickNamePaging(nickname, page);
    }

    public User selectByPrimaryKey(Integer userId) {

        if (userId == null) return null;
        return userMapper.selectByPrimaryKey(userId);
    }

    public List<User> selectByUserIdBatch(List<Integer> list) {
        if (list.size() == 0) {
            return List.of();
        }
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

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public int update(User user){
        return userMapper.update(user);
    }

    private User generateUser(RegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword().toUpperCase());
        return user;
    }

    private UserPermission getDefaultPermission() {
        UserPermission userPermission = new UserPermission();
        userPermission.setContentPublish(false);
        return userPermission;
    }

    private void setCurrentUser(User user) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute("user", user);
        new Thread(() -> {
            userMapper.updateLastLogin(user.getUserId());
        }).start();
    }

    private UserInfo getDefaultUserInfo() {
        UserInfo userInfo = new UserInfo();

        userInfo.setNickName("佚名");
        userInfo.setProfile("/img/anonymous.jpg");
        userInfo.setBirthday(LocalDate.now());
        userInfo.setPenYear(1);
        userInfo.setRegion("中国");
        userInfo.setGender(0);
        userInfo.setDescription("这个人很懒，没有留下介绍");
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());

        return userInfo;
    }

}
