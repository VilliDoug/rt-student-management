package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.Course;
import raisetech.student.management.domain.StudentDetail;

@Component
public class Converter {

  public List<StudentDetail> convertStudentDetails(
      List<Student> studentEntities,
      List<Course> allEnrollments) {

    //NPE checker #1 for studentEntities
    if (studentEntities == null || studentEntities.isEmpty()) {
      return Collections.emptyList();
    }

    //NPE checker #2 for allEnrollments
    List<Course> safeEnrollments = allEnrollments != null ? allEnrollments : Collections.emptyList();

    List<StudentDetail> details = new ArrayList<>();

    studentEntities.forEach(currentStudent -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(currentStudent);

      List<Course> matchingEnrollments = safeEnrollments.stream()
          .filter (enrollment -> Objects.equals(currentStudent.getId(), enrollment.getStudentId()))
          .collect(Collectors.toList());

      studentDetail.setStudentCourses(matchingEnrollments);
      details.add(studentDetail);
    });
    return details;
  }

  public StudentDetail convertSingleStudentDetail
      (Student student, List<Course> studentCourses)  {
    StudentDetail singleStudentDetail = new StudentDetail();
    if (student == null) {
      return singleStudentDetail;
    }
    singleStudentDetail.setStudent(student);
//    this is a less cluttered, more modern way to do what was done in NPE checker #2 above. Nice
    singleStudentDetail.setStudentCourses(java.util.Objects.requireNonNullElse(studentCourses, Collections.emptyList()));
    return singleStudentDetail;
  }

}
