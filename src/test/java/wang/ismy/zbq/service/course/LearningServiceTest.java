package wang.ismy.zbq.service.course;

import ch.qos.logback.classic.util.ContextSelectorStaticBinder;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.InterpolationTermState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.dao.course.LearningMapper;
import wang.ismy.zbq.model.dto.course.LearningNumberDTO;
import wang.ismy.zbq.model.entity.course.Course;
import wang.ismy.zbq.model.entity.course.Learning;
import wang.ismy.zbq.model.entity.course.Lesson;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.service.user.UserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("学习服务测试")
class LearningServiceTest {

    @Mock
    LearningMapper learningMapper;

    @Mock
    LessonService lessonService;

    @Mock
    CourseService courseService;

    @Mock
    UserService userService;

    @InjectMocks
    LearningService learningService;

    /**
     * @see LearningService#createLearningRecord(Integer)
     */
    @Test
    public void 测试创建学习记录() {
        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        when(lessonService.selectRawByPrimaryKey(eq(1))).thenReturn(
                Lesson.builder().courseId(1).build()
        );

        when(courseService.selectByPrimaryKey(eq(1))).thenReturn(
                Course.builder().courseId(1).build()
        );

        when(learningMapper.insertNew(argThat(l ->
                l.getCourseId().equals(1)
                        && l.getUser().getUserId().equals(1)
                        && l.getLessonId().equals(1)
        ))).thenReturn(1);

        learningService.createLearningRecord(1);

    }

    /**
     * @see LearningService#deleteLearning(Integer)
     */
    @Test
    public void 测试删除学习记录() {
        when(learningMapper.selectByPrimaryKey(eq(1))).thenReturn(
                Learning.builder().learningId(1).user(User.convert(1)).build()
        );

        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        when(learningMapper.deleteByPrimaryKey(eq(1))).thenReturn(1);

        learningService.deleteLearning(1);
    }

    /**
     * @see LearningService#calcLearningProgress(Integer)
     */
    @Test
    public void 测试计算课程学习进度() {
        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        when(lessonService.countLessonByCourseId(eq(1))).thenReturn(100L);

        when(learningMapper.countLearningByCourseIdAndUserId(eq(1), eq(1))).thenReturn(5L);

        BigDecimal bd = new BigDecimal(5.00);
        bd = bd.setScale(2, RoundingMode.HALF_DOWN);

        assertEquals(bd, learningService.calcLearningProgress(1));
    }

    /**
     * @see LearningService#calcCurrentUserLearningProgressInBatch(List)
     */
    @Test
    public void 测试批量计算当前用户课程学习进度() {

        var list = List.of(1, 2, 3);
        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        when(learningMapper.countLearningByUserIdAndCourseIdList(eq(1), argThat(l -> l == list))).thenReturn(
                List.of(
                        Map.of("course_id", 1, "COUNT(1)", 1L),
                        Map.of("course_id", 2, "COUNT(1)", 2L),
                        Map.of("course_id", 3, "COUNT(1)", 3L)
                )
        );

        when(lessonService.countLessonInBatch(argThat(l -> l == list))).thenReturn(
                Map.of(1, 2L, 2, 4L, 3, 6L)

        );

        var map = learningService.calcCurrentUserLearningProgressInBatch(list);
        assertEquals(BigDecimal.valueOf(50.0).setScale(2, RoundingMode.HALF_DOWN), map.get(1));
        assertEquals(BigDecimal.valueOf(50.0).setScale(2, RoundingMode.HALF_DOWN), map.get(2));
        assertEquals(BigDecimal.valueOf(50.0).setScale(2, RoundingMode.HALF_DOWN), map.get(3));

    }

    /**
     * @see LearningService#selectLearningState(Integer, Integer, List)
     */
    @Test
    public void 测试获取学习状态() {
        var idList = List.of(1, 2, 3);
        when(learningMapper.selectLearningListByUserIdAndCourseIdAndLessonIdList(eq(1), eq(1), argThat(l -> l == idList)))
                .thenReturn(
                        List.of(
                                Learning.builder().lessonId(1).build(),
                                Learning.builder().lessonId(2).build()
                        )
                );

        var map = learningService.selectLearningState(1, 1, idList);

        assertEquals(true, map.get(1));
        assertEquals(true, map.get(2));
        assertEquals(false, map.get(3));
    }

    /**
     * @see LearningService#selectLearningNumberByCourseIdList(List)
     */
    @Test
    public void 测试批量获取学习人数() {
        var idList = List.of(1, 2, 3);
        when(learningMapper.selectLearningNumberByCourseIdList(argThat(l -> l == idList)))
                .thenReturn(
                        List.of(
                                LearningNumberDTO.builder().courseId(1).count(1L).build(),
                                LearningNumberDTO.builder().courseId(2).count(2L).build(),
                                LearningNumberDTO.builder().courseId(3).count(3L).build()
                        )
                );
        var map = learningService.selectLearningNumberByCourseIdList(idList);

        assertEquals(1L, (long) map.get(1));
        assertEquals(2L, (long) map.get(2));

    }

    /**
     * @see LearningService#countLearningByUserIdAndCourseIdList(Integer, List)
     */
    @Test
    public void 测试查询用户的学习进度() {
        var idList = List.of(1, 2, 3);

        when(learningMapper.countLearningByUserIdAndCourseIdList(eq(1), argThat(l -> l == idList)))
                .thenReturn(
                        List.of(
                                Map.of("course_id", 1, "COUNT(1)", 1L),
                                Map.of("course_id", 2, "COUNT(1)", 2L)

                        )
                );
        var map = learningService.countLearningByUserIdAndCourseIdList(1, idList);

        assertEquals(0L, (long) map.get(3));
        assertEquals(1L, (long) map.get(1));

    }

    /**
     * @see LearningService#selectCurrentUserLearningList()
     */
    @Test
    public void 测试获取当前用户学习列表() {
        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        when(learningMapper.selectLearningListByUserIdGroupByCourseId(eq(1)))
                .thenReturn(
                        List.of(Learning.builder().lessonId(1).courseId(1).build(),
                                Learning.builder().lessonId(2).courseId(2).build())
                );
        when(courseService.selectCourseListBatch(argThat(l ->
                l.size() == 2 && l.containsAll(List.of(1, 2))
        )))
                .thenReturn(
                        List.of(Course.builder().courseId(1).courseName("课程1").build(),
                                Course.builder().courseId(2).courseName("课程2").build())
                );

        when(lessonService.selectBatch(argThat(l ->
                l.size() == 2 && l.containsAll(List.of(1, 2))
        )))
                .thenReturn(
                        List.of(
                                Lesson.builder().lessonId(1).lessonName("章节1").build(),
                                Lesson.builder().lessonId(2).lessonName("章节2").build()
                        )
                );
        when(lessonService.countLessonInBatch(anyList())).thenReturn(Map.of());
        when(learningMapper.countLearningByUserIdAndCourseIdList(any(), anyList())).thenReturn(List.of());

        var list = learningService.selectCurrentUserLearningList();

        assertEquals(2, list.size());
        assertEquals("课程1", list.get(0).getCourseName());
        assertEquals("课程2", list.get(1).getCourseName());

        assertEquals("章节1", list.get(0).getLastLessonName());
        assertEquals("章节2", list.get(1).getLastLessonName());


    }

}