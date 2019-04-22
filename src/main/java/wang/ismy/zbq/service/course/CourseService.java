package wang.ismy.zbq.service.course;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.course.CourseMapper;
import wang.ismy.zbq.dto.course.CourseDTO;
import wang.ismy.zbq.entity.course.Course;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.vo.CourseLessonVO;
import wang.ismy.zbq.vo.CourseVO;
import wang.ismy.zbq.vo.LessonListVO;
import wang.ismy.zbq.vo.UserVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LearningService learningService;

    public List<CourseVO> selectAll() {
        var list = courseMapper.selectAll();

        List<CourseVO> ret = getCourseVOList(list);
        return ret;

    }

    private List<CourseVO> getCourseVOList(List<Course> list) {
        var userIdList = list.stream()
                .map(i -> i.getPublisher().getUserId())
                .collect(Collectors.toList());

        var userList = userService.selectByUserIdBatch(userIdList);

        List<CourseVO> ret = new ArrayList<>();
        for (var i : list) {
            for (var j : userList) {
                if (j.equals(i.getPublisher())) {
                    CourseVO courseVO = CourseVO.convert(i);
                    UserVO userVO = UserVO.convert(j);
                    courseVO.setPublisher(userVO);
                    ret.add(courseVO);
                    break;
                }
            }
        }

        addLearningNumber(ret);
        addLearningProgress(ret);
        return ret;
    }

    private void addLearningProgress(List<CourseVO> ret) {
        var courseIdList = ret.stream()
                .map(CourseVO::getCourseId)
                .collect(Collectors.toList());
        var map = learningService.calcCurrentUserLearningProgressInBatch(courseIdList);

        ret.forEach(e->{
            e.setCurrentProgress(map.get(e.getCourseId()));
        });
    }

    private void addLearningNumber(List<CourseVO> ret) {
        var courseIdList = ret.stream()
                .map(CourseVO::getCourseId).collect(Collectors.toList());
        var map = learningService.selectLearningNumberByCourseIdList(courseIdList);

        for (CourseVO courseVO : ret) {

            courseVO.setLearningNumber(map.get(courseVO.getCourseId()));
        }
    }

    public CourseLessonVO selectCourseLessonByCourseId(Integer courseId) {

        var currentUser = userService.getCurrentUser();

        Course course = courseMapper.selectByPrimaryKey(courseId);
        CourseLessonVO courseLessonVO = CourseLessonVO.convert(course);
        var list = lessonService.selectByCourseId(courseId);

        var lessonIdList = list.stream()
                .map(LessonListVO::getLessonId)
                .collect(Collectors.toList());

        var map = learningService.selectLearningStateByUserIdAndLessonIdList(currentUser.getUserId(),courseId,lessonIdList);

        list.stream().forEach(e->{
            e.setHasLearn(map.get(e.getLessonId()));
        });

        courseLessonVO.setLessonList(list);

        return courseLessonVO;
    }

    public List<CourseVO> selectCurrentUserCourseListByUserId(){
        var currentUser = userService.getCurrentUser();

        var list = courseMapper.selectByUserId(currentUser.getUserId());

        var userVO = UserVO.convert(currentUser);

        List<CourseVO> ret= new ArrayList<>();

        for (var i : list){
            CourseVO courseVO = CourseVO.convert(i);
            courseVO.setPublisher(userVO);
            ret.add(courseVO);
        }

        return ret;


    }

    public Course selectByPrimaryKey(Integer courseId){
        return courseMapper.selectByPrimaryKey(courseId);
    }

    public void insertNew(CourseDTO courseDTO) {

        var currentUser = userService.getCurrentUser();
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO,course);
        course.setPublisher(currentUser);

        if (courseMapper.insertNew(course) != 1){
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }


    }

    public List<Course> selectCourseListBatch(List<Integer> courseIdList){
        if (courseIdList == null || courseIdList.size() == 0){
            return List.of();
        }

        return courseMapper.selectCourseListBatch(courseIdList);
    }

    public List<UserVO> selectClassmatesByCourseId(Integer courseId){

        var userIdList = courseMapper.selectUserIdListByCourseId(courseId);

        var userList = userService.selectByUserIdBatch(userIdList);

        return userList
                .stream()
                .map(UserVO::convert)
                .collect(Collectors.toList());

    }
}
