package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.dto.LessonDTO;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.service.LessonService;

import javax.validation.Valid;
import java.lang.annotation.Target;

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
        return StringResources.CREATE_SUCCESS;
    }
}
