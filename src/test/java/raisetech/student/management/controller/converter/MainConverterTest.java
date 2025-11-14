package raisetech.student.management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.data.Course;
import raisetech.student.management.data.Student;
import raisetech.student.management.domain.StudentDetail;

@ExtendWith(MockitoExtension.class)
class MainConverterTest {

  private MainConverter sut;

  @BeforeEach
  void before() {sut = new MainConverter();}

  @Test
  void MainConverterが適切に実行すること(){
    Student student = new Student();
    Course course = new Course();

    List<Student> studentList = List.of(student);
    List<Course> courseList = List.of(course);
    List<StudentDetail> actualDetail = sut.convertDetails(studentList, courseList);

    assertThat(actualDetail).isNotNull();

  }

}