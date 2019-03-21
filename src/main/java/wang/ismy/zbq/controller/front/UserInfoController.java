package wang.ismy.zbq.controller.front;

import org.apache.ibatis.annotations.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.dto.UserInfoDTO;
import wang.ismy.zbq.service.UserInfoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("")
    @ResultTarget
    public Object updateUserInfoById(@RequestBody @Valid UserInfoDTO userInfoDTO) {
        if (userInfoService.updateUserInfo(userInfoDTO) == 1) {
            return "更新成功";
        }
        return "更新失败";
    }

    @GetMapping("/self")
    @ResultTarget
    public Object getCurrentUserInfo(){
        return userInfoService.getCurrentUserInfo();
    }
}
