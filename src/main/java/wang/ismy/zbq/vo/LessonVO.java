package wang.ismy.zbq.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.entity.Lesson;

@Data
public class LessonVO {


    private String lessonName;

    private String lessonContent;

    public static LessonVO convert(Lesson lesson){
        LessonVO vo = new LessonVO();

        BeanUtils.copyProperties(lesson,vo);

        return vo;
    }
}
