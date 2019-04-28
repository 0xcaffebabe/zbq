package wang.ismy.zbq.model.dto.course;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author my
 */
@Data
public class CourseDTO {

    @NotBlank
    private String courseName;

    @NotBlank
    private String courseImg;

    @NotBlank
    private String courseLevel;

    @NotBlank
    private String courseDesc;
}
