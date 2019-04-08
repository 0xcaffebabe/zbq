package wang.ismy.zbq.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.CourseMapper;
import wang.ismy.zbq.dto.CourseDTO;
import wang.ismy.zbq.entity.Course;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.vo.CourseLessonVO;
import wang.ismy.zbq.vo.CourseVO;
import wang.ismy.zbq.vo.UserVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private LessonService lessonService;

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
        return ret;
    }

    public CourseLessonVO selectCourseLessonByCourseId(Integer courseId) {

        Course course = courseMapper.selectByPrimaryKey(courseId);
        CourseLessonVO courseLessonVO = CourseLessonVO.convert(course);
        var list = lessonService.selectByCourseId(courseId);
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
            ErrorUtils.error(StringResources.UNKNOWN_ERROR);
        }


    }
}
