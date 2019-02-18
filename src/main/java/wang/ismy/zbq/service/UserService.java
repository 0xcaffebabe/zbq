package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wang.ismy.zbq.dao.UserMapper;
import wang.ismy.zbq.dto.RegisterDTO;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Transactional
    public int createNewUser(RegisterDTO dto){


        User user = User.builder()
                    .username(dto.getUsername())
                    .password(dto.getPassword().toUpperCase()).build();

        if (userMapper.selectByUsername(dto.getUsername()) != null){
            throw new RuntimeException("用户名已被占用");
        }
        UserInfo userInfo = UserInfo.builder()
                .nickName("佚名")
                .profile("")
                .birthday(LocalDate.now())
                .penYear(1)
                .region("中国")
                .gender(0)
                .description("这个人很懒，没有留下介绍")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        int userInfoId = userInfoService.insertUserInfo(userInfo);

        if (userInfoId < 1){
            throw new RuntimeException("未知错误");
        }

        userInfo.setUserInfoId(userInfoId);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setUserInfo(userInfo);

        int ret = userMapper.insert(user);

        // 如果登录成功直接以当前用户登录
        if (ret == 1){
            User currentUser = userMapper.selectByUsername(dto.getUsername());
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            request.getSession().setAttribute("user",currentUser);
        }
        return ret;
    }

    public void login(String username,String password){

        User user = userMapper.selectByUsername(username);
        if (user == null){
            throw new RuntimeException("登录失败,请检查用户名与密码");
        }

        if (user.getPassword().equals(password)){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            request.getSession().setAttribute("user",user);

        }else{
            throw new RuntimeException("登录失败,请检查用户名与密码");
        }
    }

    public User getCurrentUser(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object user = request.getSession().getAttribute("user");
        if (user == null){
            return null;
        }

        if (user instanceof User){
            return (User)user;
        }
        return null;
    }
}
