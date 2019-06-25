package wang.ismy.zbq.controller.user;



import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.Limit;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.dto.UserSettingObject;
import wang.ismy.zbq.model.dto.user.LoginDTO;
import wang.ismy.zbq.model.dto.user.PasswordResetDTO;
import wang.ismy.zbq.model.dto.user.RegisterDTO;
import wang.ismy.zbq.model.dto.user.UserDTO;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.action.ActionService;
import wang.ismy.zbq.service.user.*;
import wang.ismy.zbq.util.ErrorUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author my
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserStateService userStateService;

    @Autowired
    private UserLoginLogService userLoginLogService;

    @Autowired
    private ActionService actionService;

    @Autowired
    private UserSettingService userSettingService;

    @Autowired
    private UserPasswordService userPasswordService;

    @PutMapping("/register")
    @ResultTarget
    public Object register(@RequestBody @Valid RegisterDTO registerDTO){

        if (userService.createNewUser(registerDTO) == 1){
            return "注册成功";
        }
        throw new RuntimeException("注册失败");
    }

    @GetMapping("/count")
    @ResultTarget
    public Object countUser(){
        return userService.count();
    }

    @GetMapping("/online/count")
    @ResultTarget
    public Object countOnlineUser(){
        return userService.countOnline();
    }

    @PostMapping("/login")
    @ResultTarget
    public Object login(@RequestBody @Valid LoginDTO loginDTO,
                        @RequestHeader(value = "X-Real-Ip",required = false,defaultValue = "") String ip,
                        HttpServletRequest request){
        if (ip.isEmpty()){
            ip = request.getRemoteAddr();
        }
        userService.login(loginDTO.getUsername(),loginDTO.getPassword().toUpperCase(),ip);
        return "登录成功";
    }

    @DeleteMapping("/logout")
    @ResultTarget
    public Object logout(){
        userService.setCurrentUser(null);
        return "注销成功";
    }

    @GetMapping("/state")
    @ResultTarget
    public Object getOnlineState(){
        User user = userService.getCurrentUser();

        if (user != null){
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setNickName(user.getUserInfo().getNickName());
            userDTO.setProfile(user.getUserInfo().getProfile());
            userDTO.setUserId(user.getUserId());
            if (StringUtils.isEmpty(userDTO.getProfile())){
                userDTO.setProfile("img/anonymous.jpg");
            }
            return userDTO;
        }
        return null;
    }

    @GetMapping("/username")
    @ResultTarget
    public Object detectUsername(@RequestParam("username") String username){
        return userService.selectByUsername(username)==null;
    }

    @GetMapping("/nickName/{id}")
    @ResultTarget
    @MustLogin
    public Object selectUserById(@PathVariable("id") Integer id){
        return userService.selectByPrimaryKey(id).getUserInfo().getNickName();

    }

    @GetMapping("/log/top10")
    @ResultTarget
    @MustLogin
    public Object selectTop10(){
        return userLoginLogService.currentUserSelectTop10();
    }

    @GetMapping("/state/{id}")
    @ResultTarget
    public Object selectUserStateByUserId(@PathVariable("id") Integer userId){
        return userStateService.selectByUserId(userId);
    }

    @GetMapping("/online/days")
    @ResultTarget
    @MustLogin
    public Object selectCurrentUserOnlineDays(){
        return userLoginLogService.calcCurrentUserLogDays();
    }

    @GetMapping("/action/{id}")
    @ResultTarget
    @MustLogin
    public Object action(@PathVariable("id") Integer userId,
                         @RequestParam("page") Integer page,
                         @RequestParam("length") Integer length){
        return actionService.pullActions(userId,Page.of(page,length));
    }

    @GetMapping("/setting/self")
    @ResultTarget
    @MustLogin
    public Object selfSetting(){
        return new Gson().fromJson(userSettingService.selectCurrentUser().getContent(), UserSettingObject.class);
    }

    @PostMapping("/setting/self")
    @ResultTarget
    @MustLogin
    public Object updateSelfSetting(@RequestBody @Valid UserSettingObject obj){
        userSettingService.updateCurrentUser(obj);
        return R.UPDATE_SUCCESS;
    }

    @GetMapping("/password")
    @ResultTarget
    @Limit(maxRequestPerMinute = 5)
    public Object generateRestPasswordCode(@RequestParam("email") String email){
        if (!UserAccountService.isEmail(email)){
            ErrorUtils.error(R.INCORRECT_EMAIL);
        }

        userPasswordService.generateCode(email);

        return "验证码已经下发到邮箱，请登录邮箱获取";
    }

    @PostMapping("/password")
    @ResultTarget
    @Limit(maxRequestPerMinute = 3)
    public Object resetPassword(@RequestBody @Valid PasswordResetDTO dto){
        userPasswordService.resetPassword(dto);
        return "密码重置成功";
    }
}
