package wang.ismy.zbq.vo.course;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.entity.course.Lesson;

@Data
public class LessonListVO {

    private Integer lessonId;

    private String lessonName;

    private Boolean hasLearn;

    public static LessonListVO convert(Lesson lesson){
        LessonListVO vo = new LessonListVO();
        BeanUtils.copyProperties(lesson,vo);
        return vo;
    }
}
