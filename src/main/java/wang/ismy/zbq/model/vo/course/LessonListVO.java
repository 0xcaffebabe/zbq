package wang.ismy.zbq.model.vo.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.model.entity.course.Lesson;

/**
 * @author my
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonListVO {

    private Integer lessonId;

    private String lessonName;

    private Boolean hasLearn;



    public LessonListVO(Integer lessonId){
        this.lessonId = lessonId;
    }
    public static LessonListVO convert(Lesson lesson){
        LessonListVO vo = new LessonListVO();
        BeanUtils.copyProperties(lesson,vo);
        return vo;
    }
}
