package wang.ismy.zbq.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.Limit;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.model.dto.LessonDTO;
import wang.ismy.zbq.model.dto.course.LessonCommentDTO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.course.LessonService;

import javax.validation.Valid;

/**
 * @author my
 */
@RestController
@RequestMapping("/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/{id}")
    @MustLogin
    @ResultTarget
    public Object selectById(@PathVariable("id") Integer lessonId){
        return lessonService.selectByPrimaryKey(lessonId);
    }

    @PutMapping("")
    @MustLogin
    @ResultTarget
    public Object insertNew(@RequestBody @Valid LessonDTO lessonDTO){
        lessonService.insertNew(lessonDTO);
        return R.CREATE_SUCCESS;
    }

    @PutMapping("/comment")
    @MustLogin
    @ResultTarget
    @Limit(maxRequestPerMinute = 3)
    public Object publishCommennt(@RequestBody @Valid LessonCommentDTO dto){
        lessonService.publishLessonComment(dto);
        return R.COMMENT_SUCCESS;
    }


}
