package wang.ismy.zbq.service.course;

import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.course.CourseMapper;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.dto.course.CourseDTO;
import wang.ismy.zbq.model.entity.course.Course;
import wang.ismy.zbq.model.vo.course.*;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.CommentService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.model.vo.user.UserVO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
@Setter(onMethod_ =@Inject)
public class CourseService {

    private CourseMapper mapper;

    private UserService userService;

    private LessonService lessonService;

    private LearningService learningService;

    private CommentService commentService;

    public List<CourseVO> selectAll() {
        var list = mapper.selectAll();

        return getCourseVOList(list);
    }

    public CourseLessonVO selectCourseLessonByCourseId(Integer courseId) {

        var currentUser = userService.getCurrentUser();

        Course course = mapper.selectByPrimaryKey(courseId);

        UserVO author = UserVO.convert(userService.selectByPrimaryKey(course.getPublisher().getUserId()));
        CourseLessonVO courseLessonVO = CourseLessonVO.convert(course);
        courseLessonVO.setLearningCount(learningService.countLearningByCourseId(courseId));

        courseLessonVO.setAuthor(author);
        var list = lessonService.selectByCourseId(courseId);

        var lessonIdList = list.stream()
                .map(LessonListVO::getLessonId)
                .collect(Collectors.toList());

        var map = learningService.selectLearningState(currentUser.getUserId(),courseId,lessonIdList);

        list.forEach(e-> e.setHasLearn(map.get(e.getLessonId())));

        courseLessonVO.setLessonList(list);

        return courseLessonVO;
    }

    public List<CourseVO> selectCurrentUserCourseList(){
        var currentUser = userService.getCurrentUser();

        var list = mapper.selectByUserId(currentUser.getUserId());

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
        return mapper.selectByPrimaryKey(courseId);
    }

    public void insertNew(CourseDTO courseDTO) {

        var currentUser = userService.getCurrentUser();
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO,course);
        course.setPublisher(currentUser);

        if (mapper.insertNew(course) != 1){
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }


    }

    public List<Course> selectCourseListBatch(List<Integer> courseIdList){
        if (courseIdList == null || courseIdList.size() == 0){
            return List.of();
        }

        return mapper.selectCourseListBatch(courseIdList);
    }

    public List<UserVO> selectClassmatesByCourseId(Integer courseId){

        var userIdList = mapper.selectUserIdListByCourseId(courseId);
        var userList = userService.selectByUserIdBatch(userIdList);
        return userList
                .stream()
                .map(UserVO::convert)
                .collect(Collectors.toList());

    }

    public List<CourseCommentVO> selectComment(Integer courseId, Page page){

        if (mapper.selectByPrimaryKey(courseId) == null){
            ErrorUtils.error(R.TARGET_COURSE_NOT_EXIST);
        }

        var lessonList = lessonService.selectByCourseId(courseId);

        var lessonIdList = lessonList.stream()
                .map(LessonListVO::getLessonId)
                .collect(Collectors.toList());

        var commentList = commentService.selectComments(CommentTypeEnum.LESSON,lessonIdList,page);

        var courseCommentVOList = commentList.stream()
                .map(CourseCommentVO::convert)
                .collect(Collectors.toList());

        addUserVO(courseCommentVOList);
        addLessonListVO(courseCommentVOList);
        return courseCommentVOList;
    }

    private void addLessonListVO(List<CourseCommentVO> courseCommentVOList) {

        List<Integer> lessonIdList = new ArrayList<>();

        for (var i : courseCommentVOList){
            lessonIdList.add(i.getLesson().getLessonId());
        }

        var lessonList = lessonService.selectBatch(lessonIdList);

        Map<Integer,LessonListVO> lessonListVOMap = new HashMap<>();
        for (var i : lessonList){
            lessonListVOMap.put(i.getLessonId(),LessonListVO.convert(i));
        }

        for (var i : courseCommentVOList){
            i.setLesson(lessonListVOMap.get(i.getLesson().getLessonId()));
        }
    }

    private void addLearningProgress(List<CourseVO> ret) {
        var courseIdList = ret.stream()
                .map(CourseVO::getCourseId)
                .collect(Collectors.toList());
        var map = learningService.calcCurrentUserLearningProgressInBatch(courseIdList);

        ret.forEach(e-> e.setCurrentProgress(map.get(e.getCourseId())));
    }

    private void addLearningNumber(List<CourseVO> ret) {
        var courseIdList = ret.stream()
                .map(CourseVO::getCourseId).collect(Collectors.toList());
        var map = learningService.selectLearningNumberByCourseIdList(courseIdList);

        for (CourseVO courseVO : ret) {

            courseVO.setLearningNumber(map.get(courseVO.getCourseId()));
        }
    }

    private void addUserVO(List<CourseCommentVO> courseCommentVOList) {

        List<Integer> userIdList = new ArrayList<>();

        courseCommentVOList.forEach(i->{
            userIdList.add(i.getFromUser().getUserId());
            if (i.getToUser() != null){
                userIdList.add(i.getToUser().getUserId());
            }
        });

        var userList = userService.selectByUserIdBatch(userIdList);

        Map<Integer,UserVO> userVOMap = new HashMap<>();
        for (var i : userList){
            userVOMap.put(i.getUserId(),UserVO.convert(i));
        }

        for (var i : courseCommentVOList){
            i.setFromUser(userVOMap.get(i.getFromUser().getUserId()));

            if (i.getToUser() != null){
                i.setToUser(userVOMap.get(i.getToUser().getUserId()));
            }
        }

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
}
