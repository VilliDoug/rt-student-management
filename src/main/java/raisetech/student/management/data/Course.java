package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter

public class Course {

  private String id;
  private String studentId;
  private String courseName;
  private String courseStartAt;
  private String courseEndAt;

}