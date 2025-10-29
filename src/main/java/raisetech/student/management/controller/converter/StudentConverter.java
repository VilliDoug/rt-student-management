package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.StudentEntity;
import raisetech.student.management.data.CourseEnrollmentEntity;
import raisetech.student.management.domain.StudentDetail;

@Component
public class StudentConverter {

  public List<StudentDetail> convertStudentDetails(
      List<StudentEntity> studentEntities,
      List<CourseEnrollmentEntity> allEnrollments) {

    //NPE checker #1 for studentEntities
    if (studentEntities == null || studentEntities.isEmpty()) {
      return Collections.emptyList();
    }

    //NPE checker #2 for allEnrollments
    List<CourseEnrollmentEntity> safeEnrollments = allEnrollments != null ? allEnrollments : Collections.emptyList();

    List<StudentDetail> details = new ArrayList<>();

    studentEntities.forEach(currentStudent -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(currentStudent);

      List<CourseEnrollmentEntity> matchingEnrollments = safeEnrollments.stream()
          .filter (enrollment -> Objects.equals(currentStudent.getId(), enrollment.getStudentId()))
          .collect(Collectors.toList());

      studentDetail.setStudentCourses(matchingEnrollments);
      details.add(studentDetail);
    });
    return details;
  }
}
