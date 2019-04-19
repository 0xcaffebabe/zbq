package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.dto.CourseDTO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.course.CourseService;

import javax.validation.Valid;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ResultTarget
    @GetMapping("/list")
    @MustLogin
    public Object list(){

        return courseService.selectAll();
    }

    @ResultTarget
    @GetMapping("/lesson/{id}")
    @MustLogin
    public Object courseLesson(@PathVariable("id") Integer courseId){
        return courseService.selectCourseLessonByCourseId(courseId);
    }

    @ResultTarget
    @GetMapping("/self")
    @MustLogin
    public Object selectSelfCourse(){
        return courseService.selectCurrentUserCourseListByUserId();
    }

    @PutMapping("")
    @MustLogin
    @ResultTarget
    public Object publishCourse(@RequestBody @Valid CourseDTO courseDTO){
        courseService.insertNew(courseDTO);
        return R.CREATE_SUCCESS;
    }
}
