package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wang.ismy.zbq.annotations.*;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.enums.PermissionEnum;
import wang.ismy.zbq.service.FriendService;
import wang.ismy.zbq.service.UserService;

import javax.servlet.http.HttpServletRequest;
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
    @Limit(maxRequestPerMinute = 10)
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
    @ErrorPage
    public Object space(@PathVariable("id") Integer id,ModelMap modelMap){

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

    @RequestMapping("/article/{id}")
    @MustLogin
    @ErrorPage
    public Object article(@PathVariable("id") Integer articleId){

        return "article";
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

    @RequestMapping("/contents")
    @MustLogin
    @ErrorPage
    public Object contents(){
        return "contents";
    }

    @RequestMapping("/publish/content")
    @MustLogin
    @ErrorPage
    @Permission(value = PermissionEnum.PUBLISH_CONTENT,msg = "你没有发布内容的权限")
    public Object publish(){
        return "publish";
    }


    @RequestMapping("/publish/course")
    @MustLogin
    @ErrorPage
    @Permission(PermissionEnum.COURSE_PUBLISH)
    public Object publish2(){
        return "publish2";
    }


    @RequestMapping("/courses")
    @MustLogin
    @ErrorPage
    public Object courses(){
        return "courses";
    }

    @RequestMapping("/courses/{id}")
    @MustLogin
    @ErrorPage
    public Object coursesById(@PathVariable("id") Integer courseId){
        return "course";
    }

    @RequestMapping("/lessons/{id}")
    @MustLogin
    @ErrorPage
    public Object lessons(){
        return "lesson";
    }

    @RequestMapping("/courseEdit/{id}")
    @MustLogin
    @ErrorPage
    public Object courseEdit(){
        return "courseEdit";
    }

    @RequestMapping("/addLesson/{id}")
    @MustLogin
    @ErrorPage
    public Object addLesson(){
        return "addLesson";
    }

    @RequestMapping("/learnings")
    @MustLogin
    @ErrorPage
    public Object learnings(){
        return "learnings";
    }

    @RequestMapping("/account")
    @MustLogin
    @ErrorPage
    public Object account(){
        return "account";
    }


}
