package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
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
    public Object getCurrentUserStates(){
        return stateService.selectCurrentUserState();
    }
}
