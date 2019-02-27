package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class RedirectController {


    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        return "index.html";
    }

    @RequestMapping("/home")
    @MustLogin
    public Object home(ModelMap map){
        return "home";
    }

    @RequestMapping("/friends")
    @MustLogin
    public Object friends(ModelMap map){
        return "friends";
    }

    @RequestMapping("/space/{id}")
    public Object space(@PathVariable("id") Integer id,ModelMap modelMap){
        User user = userService.getCurrentUser();
        modelMap.put("user",user);
        return "space";
    }


}
