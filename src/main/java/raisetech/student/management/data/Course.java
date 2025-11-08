package raisetech.student.management.data;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Course {

  @NotBlank
  private String id;
  @NotBlank
  private String studentId;
  private String courseName;
  private String courseStartAt;
  private String courseEndAt;

}