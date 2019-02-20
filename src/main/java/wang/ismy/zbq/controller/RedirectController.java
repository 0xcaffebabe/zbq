package wang.ismy.zbq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import wang.ismy.zbq.annotations.MustLogin;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class RedirectController {

    @RequestMapping("/")
    public String home(){
        return "index.html";
    }

    @RequestMapping("/home")
    @MustLogin
    public Object date(ModelMap map){

        return "home";
    }
}
