package wang.ismy.zbq.model.vo.course;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.model.entity.course.Lesson;

/**
 * @author my
 */
@Data
public class LessonListVO {

    private Integer lessonId;

    private String lessonName;

    private Boolean hasLearn;

    public LessonListVO(){

    }

    public LessonListVO(Integer lessonId){
        this.lessonId = lessonId;
    }
    public static LessonListVO convert(Lesson lesson){
        LessonListVO vo = new LessonListVO();
        BeanUtils.copyProperties(lesson,vo);
        return vo;
    }
}
