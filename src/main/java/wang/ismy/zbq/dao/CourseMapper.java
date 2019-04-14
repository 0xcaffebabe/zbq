package wang.ismy.zbq.dao;

import wang.ismy.zbq.entity.Course;

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
}
