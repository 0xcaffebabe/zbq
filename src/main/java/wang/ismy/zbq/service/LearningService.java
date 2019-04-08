package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.LearningMapper;
import wang.ismy.zbq.entity.Course;
import wang.ismy.zbq.entity.Learning;
import wang.ismy.zbq.entity.Lesson;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class LearningService {

    @Autowired
    private LearningMapper learningMapper;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    public void createCurrentUserLearningRecord(Integer lessonId){
        var currentUser = userService.getCurrentUser();
        Lesson lesson = lessonService.selectRawByPrimaryKey(lessonId);
        if (lesson == null) ErrorUtils.error(StringResources.TARGET_LESSON_NOT_EXIST);

        Course course = courseService.selectByPrimaryKey(lesson.getCourseId());
        if (course == null) ErrorUtils.error(StringResources.TARGET_COURSE_NOT_EXIST);

        if (learningMapper.selectByCourseIdAndLessonId(course.getCourseId(),lessonId) != null){
            ErrorUtils.error(StringResources.LEARNING_EXIST);
        }

        Learning learning = new Learning();
        learning.setCourseId(course.getCourseId());
        learning.setLessonId(lessonId);
        learning.setUser(currentUser);

        if (learningMapper.insertNew(learning) != 1){
            ErrorUtils.error(StringResources.UNKNOWN_ERROR);
        }

    }

    public void deleteCurrentUserLearningByLearningId(Integer learningId){
        Learning learning = learningMapper.selectByPrimaryKey(learningId);
        var currentUser = userService.getCurrentUser();

        if (learning == null) ErrorUtils.error(StringResources.TARGET_LESSON_NOT_EXIST);
        if (!currentUser.equals(learning.getUser())) ErrorUtils.error(StringResources.PERMISSION_DENIED);

        if (learningMapper.deleteByPrimaryKey(learningId) != 1){
            ErrorUtils.error(StringResources.UNKNOWN_ERROR);
        }

    }

    public Learning selectSelfLearningById(Integer lessonId){

        var currentUser = userService.getCurrentUser();
        Learning learning = learningMapper.selectByUserIdAndLessonId(currentUser.getUserId(),lessonId);
        return learning;
    }

    public BigDecimal calcCurrentUserLearningProgree(Integer courseId){
        var currentUser = userService.getCurrentUser();
        long lessonCount = lessonService.countLessonByCourseId(courseId);

        long learningCount = learningMapper.countLearningByCourseIdAndUserId(courseId,currentUser.getUserId());

        return new BigDecimal(((double) learningCount)/lessonCount*100).setScale(2,RoundingMode.HALF_DOWN);
    }




}
