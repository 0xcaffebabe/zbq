package wang.ismy.zbq.dao.course;

import wang.ismy.zbq.entity.course.Course;

import java.util.List;

public interface CourseMapper {

    List<Course> selectAll();

    Course selectByPrimaryKey(Integer courseId);

    List<Course> selectByUserId(Integer userId);

    int insertNew(Course course);

    /**
     * 根据courseIdList批量查询出课程列表
     *
     * @param courseIdList 课程ID列表
     * @return 课程列表
     */
    List<Course> selectCourseListBatch(List<Integer> courseIdList);

    /**
     * 根据课程ID查询出学习该课程的所有用户ID
     *
     * @param courseId 课程ID
     * @return 用户ID列表
     */
    List<Integer> selectUserIdListByCourseId(Integer courseId);

}
