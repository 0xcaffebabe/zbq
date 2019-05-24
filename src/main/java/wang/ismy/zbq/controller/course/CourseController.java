package wang.ismy.zbq.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.dto.course.CourseDTO;
import wang.ismy.zbq.model.dto.course.CourseRatingDTO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.course.CourseRatingService;
import wang.ismy.zbq.service.course.CourseService;

import javax.validation.Valid;

/**
 * @author my
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRatingService courseRatingService;

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

    @GetMapping("/classmates/{courseId}")
    @MustLogin
    @ResultTarget
    public Object selectClassmates(@PathVariable("courseId") Integer courseId){

        return courseService.selectClassmatesByCourseId(courseId);
    }

    @GetMapping("/comment/{courseId}")
    @MustLogin
    @ResultTarget
    public Object selectComment(@PathVariable("courseId") Integer courseId,
                                @RequestParam("page") Integer page,
                                @RequestParam("length") Integer length){

        return courseService.selectComment(courseId,Page.of(page,length));
    }

    @PutMapping("/rating")
    @MustLogin
    @ResultTarget
    public Object rating(@RequestBody @Valid CourseRatingDTO dto){
        courseRatingService.createCurrentUserRating(dto);

        return R.RATING_SUCCESS;
    }

    @GetMapping("/rating/{course}")
    @MustLogin
    @ResultTarget
    public Object ratingList(@PathVariable("course") Integer courseId,
                             @RequestParam("page") Integer page,
                             @RequestParam("length") Integer length){
        return courseRatingService.selectRatings(courseId,Page.of(page,length));
    }
}
