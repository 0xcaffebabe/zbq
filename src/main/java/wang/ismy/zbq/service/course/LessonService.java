package wang.ismy.zbq.service.course;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.course.LessonMapper;
import wang.ismy.zbq.model.dto.LessonDTO;
import wang.ismy.zbq.model.dto.course.LessonCommentDTO;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.model.entity.course.Course;
import wang.ismy.zbq.model.entity.course.Lesson;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.CommentService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.model.vo.course.LessonListVO;
import wang.ismy.zbq.model.vo.course.LessonVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
public class LessonService {

    @Autowired
    private LessonMapper lessonMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private LearningService learningService;

    @Autowired
    private CommentService commentService;

    public List<LessonListVO> selectAll(){

        var list = lessonMapper.selectAll();

        return list.stream()
                .map(LessonListVO::convert)
                .collect(Collectors.toList());
    }

    public List<LessonListVO> selectByCourseId(Integer courseId){

        var currentUser = userService.getCurrentUser();
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
            ErrorUtils.error(R.TARGET_COURSE_NOT_EXIST);
        }

        if (!course.getPublisher().equals(currentUser)){
            ErrorUtils.error(R.PERMISSION_DENIED);
        }

        Lesson lesson = new Lesson();
        BeanUtils.copyProperties(lessonDTO,lesson);

        if (lessonMapper.insertNew(lesson) != 1){
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }
    }

    public Lesson selectRawByPrimaryKey(Integer lessonId){
        return lessonMapper.selectByPrimaryKey(lessonId);
    }

    public long countLessonByCourseId(Integer courseId) {
        return lessonMapper.countLessonByCourseId(courseId);
    }

    public Map<Integer,Long> countLessonInBatch(List<Integer> courseIdList){

        if (courseIdList == null || courseIdList.size() == 0){
            return Map.of();
        }

        var list = lessonMapper.countLessonInBatch(courseIdList);

        Map<Integer,Long> map = new HashMap<>(16);

        for (var i : courseIdList){
            map.put(i,0L);
        }

        for (var i: list){
            map.put((Integer) i.get("course_id"),(Long) i.get("COUNT(1)"));
        }
        return map;
    }

    public List<Lesson> selectBatch(List<Integer> lessonIdList){
        if (lessonIdList == null || lessonIdList.size() == 0){
            return List.of();
        }
        return lessonMapper.selectBatch(lessonIdList);
    }

    public void publishLessonComment(LessonCommentDTO dto){

        Lesson lesson = lessonMapper.selectByPrimaryKey(dto.getLessonId());

        if (lesson == null){
            ErrorUtils.error(R.TARGET_LESSON_NOT_EXIST);
        }

        if (dto.getToUser() != null){

            User toUser = userService.selectByPrimaryKey(dto.getToUser());
            if (toUser == null){
                ErrorUtils.error(R.TARGET_USER_NOT_EXIST);
            }
        }

        User fromUser = userService.getCurrentUser();

        Comment comment = Comment.convert(dto,fromUser);

        if (commentService.createNewCommentRecord(comment) != 1){
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }

    }
}
