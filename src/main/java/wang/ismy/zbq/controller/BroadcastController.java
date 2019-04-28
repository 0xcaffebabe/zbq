package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.Limit;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.dto.BroadcastDTO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.BroadcastService;

import javax.validation.Valid;

/**
 * @author my
 */
@RestController
@RequestMapping("/broadcast")
public class BroadcastController {

    @Autowired
    private BroadcastService broadcastService;

    @PutMapping("")
    @MustLogin
    @Limit(maxRequestPerMinute = 5)
    @ResultTarget
    public Object put(@RequestBody @Valid BroadcastDTO dto){

        broadcastService.currentUserBroadcast(dto);

        return R.BROADCAST_SUCCESS;
    }

    @GetMapping("/list")
    @MustLogin
    @ResultTarget
    public Object getList(){
        return broadcastService.selectNewest10();
    }
}
