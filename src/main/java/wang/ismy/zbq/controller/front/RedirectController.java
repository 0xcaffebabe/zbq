package wang.ismy.zbq.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wang.ismy.zbq.annotations.ErrorPage;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.Permission;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.enums.PermissionEnum;
import wang.ismy.zbq.service.FriendService;
import wang.ismy.zbq.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class RedirectController {


    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @RequestMapping("/")
    public String index(){
        return "index.html";
    }

    @RequestMapping("/home")
    @MustLogin
    @ErrorPage
    public Object home(ModelMap map){
        return "home";
    }

    @RequestMapping("/friends")
    @MustLogin
    @ErrorPage
    public Object friends(ModelMap map){
        return "friends";
    }

    @RequestMapping("/space/{id}")
    public Object space(@PathVariable("id") Integer id,ModelMap modelMap){
        User user = userService.getCurrentUser();
        modelMap.put("user",user);
        return "space";
    }

    @RequestMapping("/chat/{id}")
    @MustLogin
    @ErrorPage
    public Object chat(@PathVariable("id") Integer id,ModelMap modelMap){
        if (!friendService.isFriend(userService.getCurrentUser().getUserId(),id)){
            throw new RuntimeException("对方不是你的朋友");
        }
        User user = userService.getCurrentUser();

        modelMap.put("id",user.getUserId());

        return "chat";
    }

    @RequestMapping("/states")
    @MustLogin
    @ErrorPage
    public Object state(){
        return "state";
    }

    @RequestMapping("/maps")
    @MustLogin
    @ErrorPage
    public Object maps(){
        return "maps";
    }

    @RequestMapping("/main")
    @MustLogin
    @ErrorPage
    @Permission(value = PermissionEnum.LOGIN,msg = "没有登录权限，请联系管理")
    public Object main(){
        return "main";
    }

}
