package raisetech.student.management;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

  @Autowired
  private StudentRepository repository;

  public static void main(String[] args) {
    // localhost:8080
    SpringApplication.run(Application.class, args);
  }

  //課題
  //セレクトのところを1件の取得から、WHERE文以降消して、一覧全件取得する。
  //リストのstudentをとって、全体を出力する。

  @GetMapping("/student")
  // public String getStudent(@RequestParam("name") String name) { 1 ROW ONLY RETURNS
  //return student.getName() + " " + student.getAge() + "歳"; IF RETURN NEEDED
  public String getStudent() {
    List<Student> studentList = repository.findAllStudents();
    String resultList = "";
    for (Student student: studentList) {
      resultList += student.getName() + " " + student.getAge() + "歳";
    }
    return resultList;
  }

  @PostMapping("/student")
  public void registerStudent(String name, int age) {
    repository.registerStudent(name, age);
  }

  @PatchMapping("/student")
  public void updateStudentName(String name, int age) {
    repository.updateStudent(name, age);
  }

  @DeleteMapping("/student")
  public void deleteStudent(String name) {
    repository.deleteStudent(name);

  }

}

//GET POST
//GETは取得する、リクエストの結果を受け取る
//POSTは情報を与える、渡す


