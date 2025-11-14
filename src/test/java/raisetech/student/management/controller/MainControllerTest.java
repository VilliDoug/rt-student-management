package raisetech.student.management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.student.management.data.Course;
import raisetech.student.management.data.Student;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.MainService;

@SuppressWarnings("removal")
@WebMvcTest(MainController.class)
class MainControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private MainService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  /**
   * studentList 200 test
   *
   * @throws Exception
   */
  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  /**
   * studentList 500 test
   */
  @Test
  void 受講生詳細の一覧検索が内部エラー発生すること() throws Exception {
    when(service.searchStudentList())
        .thenThrow(new RuntimeException("内部サーバー エラーが発生しました。"));

    mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
        .andExpect(status().isInternalServerError());

    verify(service, times(1)).searchStudentList();
  }

  /**
   * student/{id} correct input test 200
   */
  @Test
  void 受講生詳細の受講生で適切な値を入力したとき入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("1");
    student.setName("TestName");
    student.setEmailAddress("test@example.com");
    student.setAge(20);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);

  }

  /**
   * student/{id} invalid ID input test 400
   */
  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId("Test");
    student.setName("TestName");
    student.setEmailAddress("test@example.com");
    student.setAge(20);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("数字のみ入力するようにしてください。");

  }

  /**
   * student/{id} 404 no student found
   */
  @Test
  void 受講生詳細の検索をIDで検索する時_受講生詳細がないこと() throws Exception {
    String id = "436";
    when(service.searchStudentId(id))
        .thenReturn(null);

    mockMvc.perform(MockMvcRequestBuilders.get("/student/436"))
        .andExpect(status().isNotFound());

    verify(service, times(1)).searchStudentId(id);

  }

  /**
   * student/{id} 500
   */
  @Test
  void 受講生詳細の検索をIDで検索する時_内部エラー発生すること() throws Exception {
    String id = "999";
    when(service.searchStudentId(id))
        .thenThrow(new RuntimeException("内部サーバー エラーが発生しました。"));

    mockMvc.perform(MockMvcRequestBuilders.get("/student/999"))
        .andExpect(status().isInternalServerError());

    verify(service, times(1)).searchStudentId(id);

  }

  /**
   * registerStudent 200 test returns student detail
   *
   * @throws Exception
   */
  @Test
  void 受講生詳細を適切に登録した時_受講生詳細情報を返ってくること() throws Exception {
    Student student = new Student();
    student.setId("999");
    student.setEmailAddress("test@example.com");
    student.setAge(20);

    Course course = new Course();
    course.setCourseName("Crash Test Course");
    List<Course> courseList = List.of(course);


    StudentDetail expectedDetail = new StudentDetail(student, courseList);

    String jsonBody = objectMapper.writeValueAsString(expectedDetail);

    when(service.registerStudent(Mockito.any(StudentDetail.class)))
        .thenReturn(expectedDetail);

    mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON).content(jsonBody))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonBody));
    verify(service, times(1))
        .registerStudent(Mockito.any(StudentDetail.class));

  }

  /**
   * registerStudent throws 400 bad input (ID) test
   *
   * @throws Exception
   */
  @Test
  void 受講生詳細の登録した時_IDに数字以外を用いた時にバリデーションエラーが発生する() throws Exception {
    Student student = new Student();
    student.setId("Test");
    student.setEmailAddress("test@example.com");
    student.setAge(20);

    Course course = new Course();
    course.setCourseName("Crash Test Course");
    List<Course> courseList = List.of(course);

    StudentDetail expectedDetail = new StudentDetail(student, courseList);

    String jsonBody = objectMapper.writeValueAsString(expectedDetail);

    mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON).content(jsonBody))
        .andExpect(status().isBadRequest())
        .andDo(result ->
            verify(service, times(0))
                .registerStudent(Mockito.any(StudentDetail.class)))
        .andExpect(jsonPath("$.validationErrors['student.id']").exists())
        .andExpect(jsonPath("$.validationErrors['student.id']")
            .value("数字のみ入力するようにしてください。"));

  }

  /**
   * /registerStudent 500 test
   * @throws Exception
   */
  @Test
  void 受講生詳細の登録した時_内部エラーが発生すること() throws Exception {
    Student student = new Student();
    student.setId("1");
    student.setEmailAddress("test@example.com");
    student.setAge(20);
    Course course = new Course();
    course.setCourseName("Crash Test Course");
    List<Course> courseList = List.of(course);
    StudentDetail expectedDetail = new StudentDetail(student, courseList);
    String jsonBody = objectMapper.writeValueAsString(expectedDetail);

    when(service.registerStudent(Mockito.any(StudentDetail.class)))
        .thenThrow(new RuntimeException("内部サーバー エラーが発生しました。"));

    mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonBody))
        .andExpect(status().isInternalServerError())
        .andDo(result ->
            verify(service, times(1))
                .registerStudent(Mockito.any(StudentDetail.class)))
        .andExpect(jsonPath("$.errorResponse")
            .value("内部サーバー エラーが発生しました。"));

  }

  /**
   * /updateStudent 200 response ok
   */
  @Test
  void 受講生詳細を適切に更新した時_受講生詳細情報を返ってくること() throws Exception {
    Student student = new Student();
    student.setId("999");
    student.setEmailAddress("test@example.com");
    student.setAge(20);

    Course course = new Course();
    course.setCourseName("Crash Test Course");
    List<Course> courseList = List.of(course);

    StudentDetail expectedDetail = new StudentDetail(student, courseList);

    String jsonBody = objectMapper.writeValueAsString(expectedDetail);

    mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON).content(jsonBody))
        .andExpect(status().isOk())
        .andExpect(content().string("更新処理が成功しました。"));
    verify(service, times(1))
        .updateStudent(Mockito.any(StudentDetail.class));

  }


  /**
   * updateStudent 400 bad email input
   * @throws Exception
   */
  @Test
  void 受講生詳細を更新した時_不正なメールアドレスを入力した時にバリデーションエラーが発生すること() throws Exception {
    Student student = new Student();
    student.setId("999");
    student.setEmailAddress("testexample.com");
    student.setAge(20);

    Course course = new Course();
    course.setCourseName("Crash Test Course");
    List<Course> courseList = List.of(course);

    StudentDetail expectedDetail = new StudentDetail(student, courseList);

    String jsonBody = objectMapper.writeValueAsString(expectedDetail);

    mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON).content(jsonBody))
        .andExpect(status().isBadRequest())
        .andDo(result ->
            verify(service, times(0))
                .updateStudent(Mockito.any(StudentDetail.class)))
        .andExpect(jsonPath("$.validationErrors['student.emailAddress']")
            .exists())
        .andExpect(jsonPath("$.validationErrors['student.emailAddress']")
            .value("有効なメールアドレスを入力してください。"));

  }

  /**
   * /updateStudent 500 test
   * @throws Exception
   */
  @Test
  void 受講生詳細の更新した時_内部エラーが発生すること() throws Exception {
    Student student = new Student();
    student.setId("1");
    student.setEmailAddress("test@example.com");
    student.setAge(20);

    Course course = new Course();
    course.setCourseName("Crash Test Course");
    List<Course> courseList = List.of(course);

    StudentDetail expectedDetail = new StudentDetail(student, courseList);

    String jsonBody = objectMapper.writeValueAsString(expectedDetail);

    doThrow(new RuntimeException("内部サーバー エラーが発生しました。"))
    .when(service)
        .updateStudent(Mockito.any(StudentDetail.class));

    mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody))
        .andExpect(status().isInternalServerError())
        .andDo(result ->
            verify(service, times(1))
                .updateStudent(Mockito.any(StudentDetail.class)))
        .andExpect(jsonPath("$.errorResponse")
            .value("内部サーバー エラーが発生しました。"));

  }

}