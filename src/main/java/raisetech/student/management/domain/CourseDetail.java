package raisetech.student.management.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management.data.ApplicationStatus;
import raisetech.student.management.data.Course;

@Schema(description = "一つのコース情報と申込状況情報")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CourseDetail {

  @Valid
  private Course course;

  @Valid
  private ApplicationStatus applicationStatus;

}
