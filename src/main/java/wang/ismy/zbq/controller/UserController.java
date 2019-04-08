package wang.ismy.zbq.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.dto.LoginDTO;
import wang.ismy.zbq.dto.RegisterDTO;
import wang.ismy.zbq.dto.UserDTO;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/register")
    @ResultTarget
    public Object register(@RequestBody @Valid RegisterDTO registerDTO){

        if (userService.createNewUser(registerDTO) == 1){
            return "注册成功";
        }
        throw new RuntimeException("注册失败");
    }

    @PostMapping("/login")
    @ResultTarget
    public Object login(@RequestBody @Valid LoginDTO loginDTO){
        userService.login(loginDTO.getUsername(),loginDTO.getPassword().toUpperCase());
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

}
