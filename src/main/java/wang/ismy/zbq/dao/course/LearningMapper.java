package wang.ismy.zbq.dao.course;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.dto.course.LearningNumberDTO;
import wang.ismy.zbq.model.entity.course.Learning;
import wang.ismy.zbq.model.entity.course.Lesson;

import java.util.List;
import java.util.Map;

public interface LearningMapper {

    int insertNew(Learning learning);

    /**
    * 根据课程、章节ID、用户查询学习记录
     * @param courseId 课程ID
     * @param lessonId 章节ID
     * @param user 用户ID
     * @return 学习记录实体
    *
    */
    Learning selectByCourseIdAndLessonIdAndUserId(@Param("courseId") Integer courseId,
                                                  @Param("lessonId") Integer lessonId,
                                                  @Param("user") Integer user);

    Learning selectByUserIdAndLessonId(@Param("userId") Integer userId, @Param("lessonId") Integer lessonId);

    Learning selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    long countLearningByCourseIdAndUserId(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    List<Learning> selectLearningListByUserIdAndCourseIdAndLessonIdList(@Param("userId") Integer userId,
                                                                        @Param("courseId") Integer courseId,
                                                                        @Param("list") List<Integer> idList);

    List<LearningNumberDTO> selectLearningNumberByCourseIdList(List<Integer> courseIdList);

    List<Map<String, Object>> countLearningByUserIdAndCourseIdList(@Param("userId") Integer userId,
                                                                   @Param("list") List<Integer> courseIdList);

    /**
     * 根据用户ID查询出该用户对应的课程学习记录
     *
     * @param userId 用户ID
     * @return 课程学习记录列表
     */
    List<Learning> selectLearningListByUserIdGroupByCourseId(Integer userId);

    Long countLearningByCourseId(Integer courseId);

    /**
    * 分页查询用户学习记录（根据时间降序）
    * @param userId 用户ID
     * @param page 分页组件
     * @return 学习记录列表
    */
    List<Learning> selectByUserPaging(@Param("user") Integer userId, @Param("page") Page page);
}
