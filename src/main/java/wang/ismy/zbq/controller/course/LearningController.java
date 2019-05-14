package wang.ismy.zbq.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.course.LearningService;

/**
 * @author my
 */
@RestController
@RequestMapping("/learning")
public class LearningController {

    @Autowired
    private LearningService learningService;

    @PutMapping("/{id}")
    @ResultTarget
    @MustLogin
    public Object createLearningRecord(@PathVariable("id") Integer lessonId){

        learningService.createLearningRecord(lessonId);
        return R.CREATE_SUCCESS;
    }

    @GetMapping("/self/{id}")
    @ResultTarget
    @MustLogin
    public Object selectSelfLearningById(@PathVariable("id") Integer lessonId){
        return learningService.selectSelfLearning(lessonId);
    }

    @GetMapping("/self/list")
    @ResultTarget
    @MustLogin
    public Object selectCurrentUserLearningList(){
        return learningService.selectCurrentUserLearningList();
    }

    @DeleteMapping("/self/{id}")
    @ResultTarget
    @MustLogin
    public Object delete(@PathVariable("id") Integer learningId){
        learningService.deleteLearning(learningId);
        return R.DELETE_SUCCESS;
    }

    @GetMapping("/self/progress/{id}")
    @ResultTarget
    @MustLogin
    public Object calcProgress(@PathVariable("id") Integer courseId){
        return learningService.calcLearningProgress(courseId);
    }
}
