package raisetech.student.management;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

  @Autowired
  private StudentRepository repository;

  private Map<String, String> studentMap = new HashMap<>();

  //try to add and update students inside the map

	public static void main(String[] args) {
    // localhost:8080
    SpringApplication.run(Application.class, args);
	}

  //課題
  //セレクトのところを1件の取得から、WHERE文以降消して、一覧全件取得する。
  //リストのstudentをとって、全体を出力する。

  @GetMapping("/mapInfo")
  public Map<String, String> getMapInfo() {
    return studentMap;
  }

  @GetMapping("/student")
  public String getStudent(@RequestParam("name") String name) {
    Student student = repository.searchByName(name);
    return student.getName() + " " + student.getAge() + "歳";
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
  public void  deleteStudent(String name){
    repository.deleteStudent(name);

  }

  @PostMapping("/mapInfo")
  public void addMapInfo (String name, String age) {
    studentMap.put(name, age);
  }
}

//GET POST
//GETは取得する、リクエストの結果を受け取る
//POSTは情報を与える、渡す


