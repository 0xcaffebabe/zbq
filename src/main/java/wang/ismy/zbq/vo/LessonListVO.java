package wang.ismy.zbq.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.entity.Lesson;

@Data
public class LessonListVO {

    private Integer lessonId;

    private String lessonName;

    public static LessonListVO convert(Lesson lesson){
        LessonListVO vo = new LessonListVO();
        BeanUtils.copyProperties(lesson,vo);
        return vo;
    }
}
