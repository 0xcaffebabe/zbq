package wang.ismy.zbq.dao;

import wang.ismy.zbq.entity.Lesson;

import java.util.List;

public interface LessonMapper {

    List<Lesson> selectAll();

    List<Lesson> selectByCourseId(Integer courseId);

    Lesson selectByPrimaryKey(Integer lessonId);

    int insertNew(Lesson lesson);

    long countLessonByCourseId(Integer courseId);

}
