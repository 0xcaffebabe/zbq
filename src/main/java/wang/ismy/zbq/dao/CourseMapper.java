package wang.ismy.zbq.dao;

import wang.ismy.zbq.entity.Course;

import java.util.List;

public interface CourseMapper {

    List<Course> selectAll();

    Course selectByPrimaryKey(Integer courseId);

    List<Course> selectByUserId(Integer userId);

    int insertNew(Course course);

}
