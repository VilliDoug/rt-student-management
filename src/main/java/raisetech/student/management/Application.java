package raisetech.student.management;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

  private String name = "Gui Migotto";
  private String age = "36";

  private Map<String, String> studentMap = new HashMap<>();


  //try to add and update students inside the map

	public static void main(String[] args) {
    // localhost:8080
    SpringApplication.run(Application.class, args);

	}
   //マップの管理について少しAIを使って書いてみましたが、先のことに行ってしまった感じがします。
   //JSONの話やコマンドラインについてたくさんの情報だしてきましたので、混乱中です。一旦ここで今回おわりにします。
  @GetMapping("/mapInfo")
  public Map<String, String> getMapInfo() {
    return studentMap;
  }

  //{"timestamp":"2025-10-21T00:03:16.351+00:00","status":400,"error":"Bad Request","path":"/mapInfo"}

  @GetMapping("/studentInfo")
  public String getStudentInfo() {
    return name + " " + age + "歳";
  }

  @PostMapping("/studentInfo")
  public void setStudentInfo(String name, String age) {
    this.name = name;
    this.age = age;
  }

  @PostMapping("/studentName")
  public void updateStudentName(String name) {
    this.name = name;
  }
  //GET POST
  //GETは取得する、リクエストの結果を受け取る
  //POSTは情報を与える、渡す
}


