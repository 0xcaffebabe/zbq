package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.user.UserAccountService;

/**
 * @author my
 */
@RestController
@RequestMapping("/userAccount/")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PutMapping("")
    @MustLogin
    @ResultTarget
    public Object bindEmail(@RequestBody String email){
        email = email.replaceAll("\"","");
        userAccountService.currentUserBindEmail(email);
        return R.EMAIL_BIND_SUCCESS;
    }

    @GetMapping("/valid")
    @ResponseBody
    public Object validEmail(@RequestParam("username") String username,@RequestParam("email") String email,@RequestParam("sign") String sign){

        if (userAccountService.validEmailBinding(username,email,sign)){
            return "邮箱已验证成功";
        }else{
            return "邮箱验证成功";
        }
    }

    @GetMapping("/list")
    @MustLogin
    @ResultTarget
    public Object getCurrentUserAccountList(){
        return userAccountService.getCurrentUserAccountList();
    }

}
