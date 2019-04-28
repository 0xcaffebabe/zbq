package wang.ismy.zbq.dao.course;

import wang.ismy.zbq.model.entity.course.Lesson;

import java.util.List;
import java.util.Map;

public interface LessonMapper {

    List<Lesson> selectAll();

    List<Lesson> selectByCourseId(Integer courseId);

    Lesson selectByPrimaryKey(Integer lessonId);

    int insertNew(Lesson lesson);

    long countLessonByCourseId(Integer courseId);

    List<Map<String,Object>> countLessonInBatch(List<Integer> courseIdList);

    /**
    * 根据lessonId批量查询
    * @param lessonIdList 主键列表
     * @return 章节列表
    */
    List<Lesson> selectBatch(List<Integer> lessonIdList);
}
