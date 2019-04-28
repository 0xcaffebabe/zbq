package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.Limit;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.dto.state.StateCommentDTO;
import wang.ismy.zbq.model.dto.state.StateDTO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.StateService;
import wang.ismy.zbq.util.ErrorUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("/state")
public class StateController {

    @Autowired
    private StateService stateService;

    @PutMapping("")
    @ResultTarget
    @MustLogin
    @Limit(maxRequestPerMinute = 2)
    public Object publishState(@RequestBody @Valid StateDTO stateDTO){
        stateService.publishState(stateDTO);
        return R.STATE_PUBLISH_SUCCESS;
    }

    @GetMapping("/self")
    @ResultTarget
    @MustLogin
    public Object getCurrentUserStates(@RequestParam("page") Integer pageNo, @RequestParam("length")Integer length){
        Page p = new Page();
        p.setPageNumber(pageNo);
        p.setLength(length);
        return stateService.selectState(p);
    }

    @GetMapping("/self/count")
    @ResultTarget
    @MustLogin
    public Object countSelfState(){
        return stateService.countSelfState();
    }

    @PutMapping("/comment")
    @ResultTarget
    @MustLogin
    public Object createCurrentUserStateComment(@Valid @RequestBody StateCommentDTO stateCommentDTO){
        if (stateService.publishComment(stateCommentDTO) == 1){
            return "评论成功";
        }
        return "评论失败";
    }

    @DeleteMapping("/{stateId}")
    @ResultTarget
    @MustLogin
    public Object deleteState(@PathVariable Integer stateId){
        if (stateService.deleteState(stateId) != 1){
            ErrorUtils.error(R.DELETE_STATE_FAIL);
        }
        return R.DELETE_STATE_SUCCESS ;
    }
}
