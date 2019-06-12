package wang.ismy.zbq.service.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.dao.user.UserMapper;
import wang.ismy.zbq.model.dto.message.MessageDTO;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.dto.user.RegisterDTO;
import wang.ismy.zbq.model.entity.user.UserLoginLog;
import wang.ismy.zbq.model.entity.user.UserPermission;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.handler.SessionListener;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.*;
import wang.ismy.zbq.service.friend.FriendService;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.model.vo.user.UserVO;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author my
 */
@Service
public class UserService {

    @Setter(onMethod_ = @Inject)
    private UserMapper mapper;

    @Setter(onMethod_ = @Inject)
    private UserInfoService userInfoService;

    @Setter(onMethod_ = @Inject)
    private PermissionService permissionService;

    @Setter(onMethod_ = @Inject)
    private LoginACLService loginACLService;

    @Setter(onMethod_ = @Inject)
    private FriendService friendService;

    @Setter(onMethod_ = @Inject)
    private MessageService messageService;

    @Setter(onMethod_ = @Inject)
    private ExecuteService executeService;

    @Setter(onMethod_ = @Inject)
    private UserLoginLogService userLoginLogService;

    @Setter(onMethod_ = @Inject)
    private SessionListener sessionListener;

    @Setter(onMethod_ = @Inject)
    private SessionService sessionService;

    @Transactional(rollbackFor = Exception.class)
    public int createNewUser(RegisterDTO dto) {
        User user = generateUser(dto);

        if (mapper.selectByUsername(dto.getUsername()) != null) {
            ErrorUtils.error(R.USERNAME_OCCUPY);
        }
        UserInfo userInfo = getDefaultUserInfo();
        int userInfoId = userInfoService.insertUserInfo(userInfo);

        if (userInfoId < 1) {
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }

        userInfo.setUserInfoId(userInfoId);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setUserInfo(userInfo);

        UserPermission userPermission = getDefaultPermission();
        permissionService.insertPermission(userPermission);
        user.setUserPermission(userPermission);

        int ret = mapper.insert(user);

        // 插入一条登录权限
        if (loginACLService.insertNew(user.getUserId()) != 1) {
            ErrorUtils.error(R.UNKNOWN_ERROR);
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
            User currentUser = mapper.selectByUsername(dto.getUsername());
            setCurrentUser(currentUser);
        }
        return ret;
    }

    public void login(String username, String password, String ip) {

        User user = mapper.selectByUsername(username);
        if (user == null) {
            ErrorUtils.error(R.USERNAME_NOT_EXIST);
        }

        if (!loginACLService.canLogin(user.getUserId())) {
            ErrorUtils.error(R.ACCOUNT_DISABLE);
        }

        if (user.getPassword().equals(password)) {
            setCurrentUser(user);
            UserLoginLog loginLog = new UserLoginLog();
            loginLog.setCreateTime(LocalDateTime.now());
            loginLog.setLoginIp(ip);
            loginLog.setLoginUser(user);
            loginLog.setLoginType(0);
            userLoginLogService.addLog(loginLog);

        } else {
            ErrorUtils.error(R.LOGIN_FAIL);
        }
    }

    @MustLogin
    public User getCurrentUser() {

        Object user = sessionService.getFromSession("user");
        if (user == null) {
            return null;
        }

        if (user instanceof User) {
            return (User) user;
        }

        return null;
    }

    public List<User> selectUserByUsernamePaging(String nickname, Page page) {
        return mapper.selectByNickNamePaging(nickname, page);
    }

    public User selectByPrimaryKey(Integer userId) {

        if (userId == null) {
            return null;
        }
        return mapper.selectByPrimaryKey(userId);
    }

    public List<User> selectByUserIdBatch(List<Integer> list) {
        if (list.size() == 0) {
            return List.of();
        }
        return mapper.selectByUserIdBatch(list);
    }

    public boolean hasLogin() {
        return getCurrentUser() != null;
    }

    public void refreshCurrentUser() {
        sessionService.putInSession("user", mapper.selectByUsername(getCurrentUser().getUsername()));
    }

    public List<User> selectAll() {
        return mapper.selectAll();
    }

    public int update(User user) {
        return mapper.update(user);
    }

    public User selectByUsername(String username) {
        return mapper.selectByUsername(username);
    }

    public void setCurrentUser(User user) {
        sessionService.putInSession("user", user);

        executeService.submit(() -> mapper.updateLastLogin(user.getUserId()));

    }

    public long count() {
        return mapper.count(); }

    public long countOnline() {
        return sessionListener.countOnLine();
    }

    public static Map<Integer, UserVO> userList2UserVOMap(List<User> userList) {
        Map<Integer, UserVO> userVOMap = new HashMap<>();
        userList.forEach(e -> {
            userVOMap.put(e.getUserId(), UserVO.convert(e));
        });
        return userVOMap;
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
