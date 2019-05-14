package wang.ismy.zbq.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.course.LearningMapper;
import wang.ismy.zbq.model.entity.course.Course;
import wang.ismy.zbq.model.entity.course.Learning;
import wang.ismy.zbq.model.entity.course.Lesson;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.model.vo.course.LearningVO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author my
 */
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

    /**
     * 以当前登录用户的身份创建一条学习记录
     *
     * @param lessonId 章节ID
     */
    public void createLearningRecord(Integer lessonId) {
        var currentUser = userService.getCurrentUser();
        Lesson lesson = lessonService.selectRawByPrimaryKey(lessonId);
        if (lesson == null) {
            ErrorUtils.error(R.TARGET_LESSON_NOT_EXIST);
        }

        Course course = courseService.selectByPrimaryKey(lesson.getCourseId());
        if (course == null) {
            ErrorUtils.error(R.TARGET_COURSE_NOT_EXIST);
        }

        var t = learningMapper.selectByCourseIdAndLessonId(course.getCourseId(), lessonId);
        if (t != null) {
            if (t.getUser().equals(currentUser)) {
                ErrorUtils.error(R.LEARNING_EXIST);
            }

        }

        Learning learning = new Learning();
        learning.setCourseId(course.getCourseId());
        learning.setLessonId(lessonId);
        learning.setUser(currentUser);

        if (learningMapper.insertNew(learning) != 1) {
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }

    }

    /**
     * 删除当前登录用户的一条学习记录
     *
     * @param learningId 学习记录ID
     */
    public void deleteLearning(Integer learningId) {
        Learning learning = learningMapper.selectByPrimaryKey(learningId);
        var currentUser = userService.getCurrentUser();

        if (learning == null) {
            ErrorUtils.error(R.TARGET_LESSON_NOT_EXIST);
        }
        if (!currentUser.equals(learning.getUser())) {
            ErrorUtils.error(R.PERMISSION_DENIED);
        }

        if (learningMapper.deleteByPrimaryKey(learningId) != 1) {
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }

    }

    /**
     * 根据章节ID查询当前登录用户学习记录
     *
     * @param lessonId 章节ID
     * @return 学习记录
     */
    public Learning selectSelfLearning(Integer lessonId) {

        var currentUser = userService.getCurrentUser();
        Learning learning = learningMapper.selectByUserIdAndLessonId(currentUser.getUserId(), lessonId);
        return learning;
    }

    /**
    * 计算当前登录用户课程学习进度
     * @param courseId 课程ID
    * @return 学习进度百分比
    */
    public BigDecimal calcLearningProgress(Integer courseId) {
        var currentUser = userService.getCurrentUser();
        long lessonCount = lessonService.countLessonByCourseId(courseId);
        long learningCount = learningMapper.countLearningByCourseIdAndUserId(courseId, currentUser.getUserId());

        if (learningCount == 0) {
            return new BigDecimal(0);
        }
        return new BigDecimal(((double) learningCount) / lessonCount * 100).setScale(2, RoundingMode.HALF_DOWN);
    }


    public Map<Integer, BigDecimal> calcCurrentUserLearningProgressInBatch(List<Integer> courseIdList) {
        var currentUser = userService.getCurrentUser();

        var lessonCountMap = lessonService.countLessonInBatch(courseIdList);
        var learningCountMap = countLearningByUserIdAndCourseIdList(currentUser.getUserId(), courseIdList);


        Map<Integer, BigDecimal> ret = new HashMap<>();
        for (var key : learningCountMap.keySet()) {
            BigDecimal b;
            long learningCount = learningCountMap.get(key);
            if (learningCount == 0) {
                b = new BigDecimal(0);
            } else {
                b = new BigDecimal(((double) learningCount) / lessonCountMap.get(key) * 100)
                        .setScale(2, RoundingMode.HALF_DOWN);
            }

            ret.put(key, b);

        }
        return ret;
    }


    /**
     * 根据用户ID、课程ID与章节ID列表查询出对应的课程学习状态
     *
     * @param userId       用户ID
     * @param courseId     课程ID
     * @param lessonIdList 章节ID列表
     */
    public Map<Integer, Boolean> selectLearningState(Integer userId,
                                                     Integer courseId,
                                                     List<Integer> lessonIdList) {
        if (lessonIdList == null || lessonIdList.size() == 0) {
            return Map.of();
        }

        var list = learningMapper.selectLearningListByUserIdAndCourseIdAndLessonIdList(userId, courseId, lessonIdList);

        Map<Integer, Boolean> ret = new HashMap<>();

        lessonIdList.stream().forEach(e -> {
            ret.put(e, false);
        });
        list.forEach(e -> {
            ret.put(e.getLessonId(), true);
        });
        return ret;
    }

    public Map<Integer, Long> selectLearningNumberByCourseIdList(List<Integer> courseIdList) {

        if (courseIdList == null || courseIdList.size() == 0) {
            return Map.of();
        }

        Map<Integer, Long> ret = new HashMap<>();

        for (Integer integer : courseIdList) {
            ret.put(integer, 0L);
        }

        var list = learningMapper.selectLearningNumberByCourseIdList(courseIdList);

        list.forEach(e -> {
            ret.put(e.getCourseId(), e.getCount());
        });
        return ret;
    }

    public Map<Integer, Long> countLearningByUserIdAndCourseIdList(Integer userId, List<Integer> courseIdList) {

        var list = learningMapper.countLearningByUserIdAndCourseIdList(userId, courseIdList);


        Map<Integer, Long> ret = new HashMap<>();

        for (Integer integer : courseIdList) {

            ret.put(integer, 0L);
        }


        for (var i : list) {
            ret.put((Integer) i.get("course_id"), (Long) i.get("COUNT(1)"));
        }
        return ret;
    }

    public List<LearningVO> selectCurrentUserLearningList() {
        var currentUser = userService.getCurrentUser();
        var list = learningMapper.selectLearningListByUserIdGroupByCourseId(currentUser.getUserId());

        List<Integer> courseIdList = new ArrayList<>();
        Map<Integer, LearningVO> learningVOCourseMap = new HashMap<>();
        Map<Integer, LearningVO> learningVOLessonMap = new HashMap<>();
        for (var i : list) {
            courseIdList.add(i.getCourseId());
            LearningVO vo = new LearningVO();
            vo.setCourseId(i.getCourseId());
            learningVOCourseMap.put(i.getCourseId(), vo);
            learningVOLessonMap.put(i.getLessonId(), vo);
        }

        // 添加课程内容
        var courseList = courseService.selectCourseListBatch(courseIdList);
        for (var i : courseList) {
            LearningVO vo = learningVOCourseMap.get(i.getCourseId());
            vo.setCourseName(i.getCourseName());
            vo.setCourseImg(i.getCourseImg());
        }

        // 添加最近学习章节
        addLastLesson(learningVOLessonMap);

        // 添加学习进度
        addLearningProgress(learningVOCourseMap);

        return new ArrayList<>(learningVOCourseMap.values());
    }

    private void addLearningProgress(Map<Integer, LearningVO> learningVOCourseMap) {

        var map = calcCurrentUserLearningProgressInBatch(
                new ArrayList<>(learningVOCourseMap.keySet())
        );

        for (var key : map.keySet()) {
            learningVOCourseMap.get(key).setLearningProgress(map.get(key));
        }

    }

    private void addLastLesson(Map<Integer, LearningVO> learningVOMap) {

        List<Integer> lessonIdList = new ArrayList<>(learningVOMap.keySet());

        var lessonList = lessonService.selectBatch(lessonIdList);

        for (var i : lessonList) {

            var learningVO = learningVOMap.get(i.getLessonId());

            if (learningVO != null) {
                learningVO.setLastLessonName(i.getLessonName());
            }
        }

    }


}
