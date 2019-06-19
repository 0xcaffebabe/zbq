package wang.ismy.zbq.service.course;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.dao.course.CourseMapper;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.dto.course.CourseDTO;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.model.entity.course.Course;
import wang.ismy.zbq.model.entity.course.Lesson;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.model.vo.course.CourseVO;
import wang.ismy.zbq.model.vo.course.LessonListVO;
import wang.ismy.zbq.service.CommentService;
import wang.ismy.zbq.service.user.UserService;

import javax.inject.Inject;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("课程服务测试")
class CourseServiceTest {

    @Mock
    CourseMapper mapper;

    @Mock
    UserService userService;

    @Mock
    LessonService lessonService;

    @Mock
    LearningService learningService;

    @Mock
    CommentService commentService;

    @InjectMocks
    CourseService courseService;

    /**
     * @see CourseService#selectCourseLessonByCourseId(Integer)
     */
    @Test
    public void 测试获取课程章节视图列表() {
        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        when(courseService.selectByPrimaryKey(eq(1))).thenReturn(
                Course.builder().courseId(1).courseName("课程1").publisher(User.convert(1)).build()
        );

        when(userService.selectByPrimaryKey(eq(1))).thenReturn(
                User.builder().userId(1).userInfo(UserInfo.builder().nickName("用户1").build()).build()
        );

        when(lessonService.selectByCourseId(eq(1))).thenReturn(
                List.of(
                        LessonListVO.builder().lessonId(1).lessonName("章节1").build(),
                        LessonListVO.builder().lessonId(2).lessonName("章节2").build()
                )
        );

        when(learningService.selectLearningState(eq(1), eq(1), argThat(l ->
                l.containsAll(List.of(1, 2))
        )))
                .thenReturn(
                        Map.of(1, false, 2, true)
                );

        var courseLessonVO = courseService.selectCourseLessonByCourseId(1);

        assertEquals("课程1", courseLessonVO.getCourseName());
        assertEquals(2, courseLessonVO.getLessonList().size());
        assertEquals(false, courseLessonVO.getLessonList().get(0).getHasLearn());

    }

    /**
     * @see CourseService#selectCurrentUserCourseList()
     */
    @Test
    public void 测试当前用户课程列表() {
        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        when(mapper.selectByUserId(eq(1)))
                .thenReturn(
                        List.of(
                                Course.builder().courseId(1).build(),
                                Course.builder().courseId(2).build()
                        )
                );

        var list = courseService.selectCurrentUserCourseList();

        assertEquals(2, list.size());
        assertEquals(1, (int) list.get(0).getCourseId());
        assertEquals(1, (int) list.get(0).getPublisher().getUserId());
    }

    /**
     * @see CourseService#insertNew(CourseDTO)
     */
    @Test
    public void 测试新增课程() {

        CourseDTO dto = new CourseDTO();
        dto.setCourseName("测试课程");
        dto.setCourseDesc("课程描述");

        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        when(mapper.insertNew(argThat(c ->
                c.getCourseName().equals("测试课程")
                        && c.getCourseDesc().equals("课程描述")
                        && c.getPublisher().getUserId().equals(1)
        )))
                .thenReturn(1);

        courseService.insertNew(dto);
    }

    /**
     * @see CourseService#selectClassmatesByCourseId(Integer)
     */
    @Test
    public void 测试查询课程同学() {
        when(mapper.selectUserIdListByCourseId(eq(1))).thenReturn(
                List.of(1, 2, 3)
        );

        when(userService.selectByUserIdBatch(argThat(l -> l.containsAll(List.of(1, 2, 3))))).thenReturn(
                List.of(
                        User.convert(1),
                        User.convert(2),
                        User.convert(3)
                )
        );

        var list = courseService.selectClassmatesByCourseId(1);

        assertEquals(3, list.size());

        assertEquals(1, (int) list.get(0).getUserId());
    }

    /**
     * @see CourseService#selectComment(Integer, Page)
     */
    @Test
    public void 测试查询课程评论() {
        when(mapper.selectByPrimaryKey(eq(1))).thenReturn(Course.builder().build());

        when(lessonService.selectByCourseId(eq(1))).thenReturn(
                List.of(
                        LessonListVO.builder().lessonId(1).build(),
                        LessonListVO.builder().lessonId(2).build(),
                        LessonListVO.builder().lessonId(3).build()
                )
        );

        when(commentService.selectComments(eq(CommentTypeEnum.LESSON), anyList(), any(Page.class)))
                .thenReturn(
                        List.of(
                                Comment.builder().commentId(1).content("评论1").topicId(1).fromUser(User.convert(1)).build(),
                                Comment.builder().commentId(1).content("评论2").topicId(2).fromUser(User.convert(2)).build()
                        )
                );

        when(userService.selectByUserIdBatch(argThat(l->l.containsAll(List.of(1,2)))))
               .thenReturn(
                       List.of(
                               User.builder().userId(1).userInfo(UserInfo.builder().nickName("用户1").build()).build(),
                               User.builder().userId(2).userInfo(UserInfo.builder().nickName("用户2").build()).build()
                       )
               );

        when(lessonService.selectBatch(argThat(l->l.containsAll(List.of(1,2)))))
                .thenReturn(
                        List.of(
                                Lesson.builder().lessonId(1).lessonName("章节1").build(),
                                Lesson.builder().lessonId(2).lessonName("章节2").build()
                        )
                );

        var list = courseService.selectComment(1,Page.of(1,5));

        assertEquals(2,list.size());

        assertEquals("章节1",list.get(0).getLesson().getLessonName());
        assertEquals(1,(int)list.get(0).getFromUser().getUserId());

    }

    /**
     * @see CourseService#getCourseVOList(List)
     */
    @Test
    public void 测试获取课程视图列表() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var courseList = List.of(
                Course.builder().courseId(1).courseName("课程1").publisher(User.convert(1)).build(),
                Course.builder().courseId(2).courseName("课程2").publisher(User.convert(2)).build(),
                Course.builder().courseId(3).courseName("课程3").publisher(User.convert(3)).build()
        );

        when(userService.selectByUserIdBatch(argThat(l->l.containsAll(List.of(1,2,3)))))
                .thenReturn(
                        List.of(
                                User.convert(1),
                                User.convert(2),
                                User.convert(3)
                        )
                );

        when(learningService.selectLearningNumberByCourseIdList(argThat(l->l.containsAll(List.of(1,2,3)))))
                .thenReturn(
                        Map.of(1,1L,2,2L,3,3L)
                );

        when(learningService.calcCurrentUserLearningProgressInBatch(argThat(l->l.containsAll(List.of(1,2,3)))))
                .thenReturn(
                        Map.of(1, BigDecimal.valueOf(5),2,BigDecimal.valueOf(10),3,BigDecimal.valueOf(15))
                );

        var method = courseService.getClass().getDeclaredMethod("getCourseVOList",List.class);
        method.setAccessible(true);
        List<CourseVO> list = (List<CourseVO>) method.invoke(courseService,courseList);

        assertEquals(3,list.size());
        assertEquals(1,(int)list.get(0).getPublisher().getUserId());
        assertEquals(BigDecimal.valueOf(5),list.get(0).getCurrentProgress());

        assertEquals(1L,(long)list.get(0).getLearningNumber());
    }
}