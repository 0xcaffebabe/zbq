package wang.ismy.zbq.dao.course;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.course.CourseRating;

import java.util.List;

/**
 * @author my
 */
public interface CourseRatingMapper {

    /**
     * 插入一条评分记录
     *
     * @param courseRating 评分实体
     * @return 受影响行数
     */
    int insertNew(CourseRating courseRating);

    /**
     * 根据课程ID和用户ID进行查询
     *
     * @param course 课程ID
     * @param user   用户ID
     * @return 评分实体
     */
    CourseRating selectByCourseAndUser(@Param("course") Integer course, @Param("user") Integer user);

    /**
     * 根据课程ID分页查询
     *
     * @param course 课程ID
     * @param page   分页组件
     * @return 评价列表
     */
    List<CourseRating> selectByCoursePaging(@Param("course") Integer course, @Param("page") Page page);
}
