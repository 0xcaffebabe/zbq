package wang.ismy.zbq.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

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
