package wang.ismy.zbq.model.vo.course;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.model.entity.course.Lesson;

/**
 * @author my
 */
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
