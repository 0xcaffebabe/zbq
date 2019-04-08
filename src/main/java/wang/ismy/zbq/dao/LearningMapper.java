package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.entity.Learning;
import wang.ismy.zbq.entity.Lesson;

public interface LearningMapper {

    int insertNew(Learning learning);

    Learning selectByCourseIdAndLessonId(@Param("courseId") Integer courseId,@Param("lessonId") Integer lessonId);

    Learning selectByUserIdAndLessonId(@Param("userId") Integer userId,@Param("lessonId") Integer lessonId);

    Learning selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    long countLearningByCourseIdAndUserId(@Param("courseId") Integer courseId,@Param("userId") Integer userId);


}
