package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.service.LearningService;

@RestController
@RequestMapping("/learning")
public class LearningController {

    @Autowired
    private LearningService learningService;

    @PutMapping("/{id}")
    @ResultTarget
    @MustLogin
    public Object createLearningRecord(@PathVariable("id") Integer lessonId){

        learningService.createCurrentUserLearningRecord(lessonId);
        return StringResources.CREATE_SUCCESS;
    }

    @GetMapping("/self/{id}")
    @ResultTarget
    @MustLogin
    public Object selectSelfLearningById(@PathVariable("id") Integer lessonId){
        return learningService.selectSelfLearningById(lessonId);
    }

    @DeleteMapping("/self/{id}")
    @ResultTarget
    @MustLogin
    public Object delete(@PathVariable("id") Integer learningId){
        learningService.deleteCurrentUserLearningByLearningId(learningId);
        return StringResources.DELETE_SUCCESS;
    }

    @GetMapping("/self/progress/{id}")
    @ResultTarget
    @MustLogin
    public Object calcProgress(@PathVariable("id") Integer courseId){
        return learningService.calcCurrentUserLearningProgree(courseId);
    }
}
