package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.ApplicationStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.Course;
import raisetech.student.management.domain.CourseDetail;
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
   * @param studentList
   * @param courseList
   * @param statusList
   * @return
   */
  public List<StudentDetail> convertDetails(
      List<Student> studentList,
      List<Course> courseList,
      List<ApplicationStatus> statusList) {
    if (studentList == null || studentList.isEmpty()) {
      return Collections.emptyList();
    }

    List<CourseDetail> courseDetailList = new ArrayList<>();

    courseList.forEach(course -> {
      CourseDetail courseDetail = new CourseDetail();
      courseDetail.setCourse(course);

      ApplicationStatus optionalStatus = statusList.stream()
          .filter(status -> Objects.equals(course.getId(), status.getCourseId()))
          .findFirst()
          .orElse(null);

      courseDetail.setApplicationStatus(optionalStatus);
      courseDetailList.add(courseDetail);
    });

    List<StudentDetail> details = new ArrayList<>();

    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<CourseDetail> matchCourseList = courseDetailList.stream()
          .filter (courseDetail -> Objects.equals(student.getId(), courseDetail.getCourse().getStudentId()))
          .collect(Collectors.toList());

      studentDetail.setCourseDetailList(matchCourseList);
      details.add(studentDetail);
    });
    return details;
  }

}
