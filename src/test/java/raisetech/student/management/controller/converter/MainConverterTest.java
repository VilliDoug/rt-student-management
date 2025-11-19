package raisetech.student.management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import raisetech.student.management.data.ApplicationStatus;
import raisetech.student.management.data.Course;
import raisetech.student.management.data.Student;
import raisetech.student.management.domain.StudentDetail;

class MainConverterTest {

  private MainConverter sut;

  @BeforeEach
  void before() {sut = new MainConverter();}

  @Test
  @DisplayName("コンバーター処理が適切に実行する時コースリストが空になるケース")
  void convertDetailsIsCalledAndCourseListIsEmpty(){
    Student student = new Student();
    student.setId("999");
    student.setName("TestName");
    student.setEmailAddress("test@example.com");
    student.setAge(20);
    Course course = new Course();
    course.setId("888");
    course.setStudentId("444");
    course.setCourseName("Crash Test Course");
    ApplicationStatus status = new ApplicationStatus();
    status.setId("1");
    status.setCourseId("888");
    status.setApplicationStatus("仮申込");

    List<Student> studentList = List.of(student);
    List<Course> courseList = List.of(course);
    List<ApplicationStatus> statusList = List.of(status);
    List<StudentDetail> actualDetail = sut.convertDetails(studentList, courseList, statusList);


    assertThat(actualDetail).hasSize(1);

    assertThat(actualDetail.get(0).getStudent())
        .extracting("id").isEqualTo("999");
    assertThat(actualDetail.get(0).getStudent())
        .extracting("name").isEqualTo("TestName");
    assertThat(actualDetail.get(0).getStudent())
        .extracting("emailAddress").isEqualTo("test@example.com");
    assertThat(actualDetail.get(0).getStudent())
        .extracting("age").isEqualTo(20);

    assertThat(actualDetail.get(0).getCourseDetailList()).isEmpty();

  }

  @Test
  @DisplayName("コンバーター処理が実行されコースリストに期待値が含まれること")
  void convertDetailsIsCalledAndCourseListContainsExpectedData(){
    Student student = new Student();
    student.setId("999");
    student.setName("TestName");
    student.setEmailAddress("test@example.com");
    student.setAge(20);
    Course course = new Course();
    course.setId("888");
    course.setStudentId("999");
    course.setCourseName("Crash Test Course");
    ApplicationStatus status = new ApplicationStatus();
    status.setId("1");
    status.setCourseId("888");
    status.setApplicationStatus("仮申込");

    List<Student> studentList = List.of(student);
    List<Course> courseList = List.of(course);
    List<ApplicationStatus> statusList = List.of(status);
    List<StudentDetail> actualDetail = sut.convertDetails(studentList, courseList, statusList);


    assertThat(actualDetail).hasSize(1);

    assertThat(actualDetail.get(0).getStudent())
        .extracting("id").isEqualTo("999");
    assertThat(actualDetail.get(0).getStudent())
        .extracting("name").isEqualTo("TestName");
    assertThat(actualDetail.get(0).getStudent())
        .extracting("emailAddress").isEqualTo("test@example.com");
    assertThat(actualDetail.get(0).getStudent())
        .extracting("age").isEqualTo(20);

    assertThat(actualDetail.get(0).getCourseDetailList()).hasSize(1);
    assertThat(actualDetail.get(0).getCourseDetailList())
        .extracting("course.id", "course.studentId", "course.courseName", "applicationStatus.applicationStatus")
        .containsExactly(tuple("888", "999", "Crash Test Course", "仮申込"));

  }

}



