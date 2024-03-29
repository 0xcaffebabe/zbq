package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wang.ismy.zbq.annotations.*;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.enums.PermissionEnum;
import wang.ismy.zbq.service.friend.FriendService;
import wang.ismy.zbq.service.user.UserService;

/**
 * @author my
 */
@Controller
public class RedirectController {


    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @RequestMapping("/")
    @Limit(maxRequestPerMinute = 10)
    public String index(){
        // 如果用户已经登陆，直接跳转到主页
        if (userService.hasLogin()){
            return "main";

        }
        return "index.html";
    }

    @RequestMapping("")
    @Limit(maxRequestPerMinute = 10)
    public String index1(){
        return index();
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
    @Permission(value = PermissionEnum.PUBLISH_CONTENT,msg = "您没有发布课程的权限，如需开通请发送申请邮件到715711877@qq.com")
    public Object publish(){
        return "publish";
    }


    @RequestMapping("/publish/course")
    @MustLogin
    @ErrorPage
    @Permission(value = PermissionEnum.COURSE_PUBLISH,msg = "您没有发布课程的权限，如需开通请发送申请邮件到715711877@qq.com")
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

    @RequestMapping("/square")
    @MustLogin
    @ErrorPage
    public Object square(){
        return "square";
    }

    @RequestMapping("/videos")
    @MustLogin
    @ErrorPage
    public Object videos(){
        return "videos";
    }

    @RequestMapping("/collections")
    @MustLogin
    @ErrorPage
    public Object collections(){
        return "collections";
    }

    @RequestMapping("/user/id/{id}")
    @MustLogin
    @ErrorPage
    public Object userId(){
        return "userId";
    }

}
