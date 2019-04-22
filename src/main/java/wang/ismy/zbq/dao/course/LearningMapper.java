package wang.ismy.zbq.dao.course;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.dto.course.LearningNumberDTO;
import wang.ismy.zbq.entity.course.Learning;

import java.util.List;
import java.util.Map;

public interface LearningMapper {

    int insertNew(Learning learning);

    Learning selectByCourseIdAndLessonId(@Param("courseId") Integer courseId, @Param("lessonId") Integer lessonId);

    Learning selectByUserIdAndLessonId(@Param("userId") Integer userId, @Param("lessonId") Integer lessonId);

    Learning selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    long countLearningByCourseIdAndUserId(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    List<Learning> selectLearningListByUserIdAndCourseIdAndLessonIdList(@Param("userId") Integer userId,
                                                                        @Param("courseId") Integer courseId,
                                                                        @Param("list") List<Integer> idList);

    List<LearningNumberDTO> selectLearningNumberByCourseIdList(List<Integer> courseIdList);

    List<Map<String, Object>> countLearningByUserIdAndCourseIdList(@Param("userId") Integer userId, @Param("list") List<Integer> courseIdList);

    /**
     * 根据用户ID查询出该用户对应的课程学习记录
     *
     * @param userId 用户ID
     * @return 课程学习记录列表
     */
    List<Learning> selectLearningListByUserIdGroupByCourseId(Integer userId);
}
