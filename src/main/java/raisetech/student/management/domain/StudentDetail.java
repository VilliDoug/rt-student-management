package raisetech.student.management.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.data.StudentEntity;
import raisetech.student.management.data.CourseEnrollmentEntity;

@Getter
@Setter
public class StudentDetail {

  private StudentEntity student;
  private List<CourseEnrollmentEntity> studentCourses;

}
