package wang.ismy.zbq.model.dto.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LearningNumberDTO {

    private Integer courseId;

    private Long count;
}
