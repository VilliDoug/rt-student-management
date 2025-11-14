package raisetech.student.management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.student.management.data.Course;
import raisetech.student.management.data.Student;
import raisetech.student.management.domain.StudentDetail;

class MainConverterTest {

  private MainConverter sut;

  @BeforeEach
  void before() {sut = new MainConverter();}

  @Test
  void コンバーター処理が適切に実行すること_コースリストが空になるケース(){
    Student student = new Student();
    student.setId("1");
    Course course = new Course();
    course.setStudentId("2");

    List<Student> studentList = List.of(student);
    List<Course> courseList = List.of(course);
    List<StudentDetail> actualDetail = sut.convertDetails(studentList, courseList);


    assertThat(actualDetail).isNotNull();
    assertThat(actualDetail).hasSize(1);
    assertThat(actualDetail.get(0).getStudent()).isEqualTo(student);
    assertThat(actualDetail.get(0).getCourseList()).isEmpty();

  }

}