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

/**
 *　受講生詳細を受講生や受講生コース情報、もしくはその逆の変換を行うコンバーターです。
 */
@Component
public class MainConverter {

  /**
   * 受講生に紐づく受講生コース情報をマッピングする。
   * 受講生コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる。
   *
   * @param studentList　受講生一覧
   * @param courseList　受講生コース情報のリスト
   * @return　受講生詳細情報のリスト
   */
  public List<StudentDetail> convertDetails(
      List<Student> studentList,
      List<Course> courseList) {
    if (studentList == null || studentList.isEmpty()) {
      return Collections.emptyList();
    }
    List<Course> safeCourseList = courseList != null ? courseList : Collections.emptyList();
    List<StudentDetail> details = new ArrayList<>();

    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<Course> matchCourseList = safeCourseList.stream()
          .filter (enrollment -> Objects.equals(student.getId(), enrollment.getStudentId()))
          .collect(Collectors.toList());

      studentDetail.setCourseList(matchCourseList);
      details.add(studentDetail);
    });
    return details;
  }

  public StudentDetail convertSingleDetail
      (Student student, List<Course> studentCourses)  {
    StudentDetail singleStudentDetail = new StudentDetail();
    if (student == null) {
      return singleStudentDetail;
    }
    singleStudentDetail.setStudent(student);
//    this is a less cluttered, more modern way to do what was done in NPE checker #2 above. Nice
    singleStudentDetail.setCourseList(java.util.Objects.requireNonNullElse(studentCourses, Collections.emptyList()));
    return singleStudentDetail;
  }

}
