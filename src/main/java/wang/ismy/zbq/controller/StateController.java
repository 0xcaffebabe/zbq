package wang.ismy.zbq.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.dto.StateDTO;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.service.StateService;

import javax.validation.Valid;

@RestController
@RequestMapping("/state")
public class StateController {

    @Autowired
    private StateService stateService;

    @PutMapping("")
    @ResultTarget
    @MustLogin
    public Object publishState(@RequestBody @Valid StateDTO stateDTO){
        stateService.currentUserPublishState(stateDTO);
        return StringResources.STATE_PUBLISH_SUCCESS;
    }

    @GetMapping("/self")
    @ResultTarget
    @MustLogin
    public Object getCurrentUserStates(@RequestParam("page") Integer pageNo, @RequestParam("length")Integer length){
        Page page = Page.builder().pageNumber(pageNo).length(length).build();
        return stateService.selectCurrentUserStatePaging(page);
    }

    @GetMapping("/self/count")
    @ResultTarget
    @MustLogin
    public Object countSelfState(){
        return stateService.countSelfState();
    }
}
