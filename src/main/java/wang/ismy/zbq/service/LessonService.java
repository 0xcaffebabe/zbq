package wang.ismy.zbq.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.LessonMapper;
import wang.ismy.zbq.dto.LessonDTO;
import wang.ismy.zbq.entity.Course;
import wang.ismy.zbq.entity.Lesson;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.vo.LessonListVO;
import wang.ismy.zbq.vo.LessonVO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {

    @Autowired
    private LessonMapper lessonMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    public List<LessonListVO> selectAll(){

        var list = lessonMapper.selectAll();

        return list.stream()
                .map(LessonListVO::convert)
                .collect(Collectors.toList());
    }

    public List<LessonListVO> selectByCourseId(Integer courseId){

        var list = lessonMapper.selectByCourseId(courseId);

        return list.stream()
                .map(LessonListVO::convert)
                .collect(Collectors.toList());
    }

    public LessonVO selectByPrimaryKey(Integer lessonId){
        return LessonVO.convert(lessonMapper.selectByPrimaryKey(lessonId));
    }

    public void insertNew(LessonDTO lessonDTO){

        Course course = courseService.selectByPrimaryKey(lessonDTO.getCourseId());

        var currentUser = userService.getCurrentUser();
        if (course == null){
            ErrorUtils.error(StringResources.TARGET_COURSE_NOT_EXIST);
        }

        if (!course.getPublisher().equals(currentUser)){
            ErrorUtils.error(StringResources.PERMISSION_DENIED);
        }

        Lesson lesson = new Lesson();
        BeanUtils.copyProperties(lessonDTO,lesson);

        if (lessonMapper.insertNew(lesson) != 1){
            ErrorUtils.error(StringResources.UNKNOWN_ERROR);
        }
    }

    public Lesson selectRawByPrimaryKey(Integer lessonId){
        return lessonMapper.selectByPrimaryKey(lessonId);
    }

    public long countLessonByCourseId(Integer courseId) {
        return lessonMapper.countLessonByCourseId(courseId);
    }
}
