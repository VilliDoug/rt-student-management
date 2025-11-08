package raisetech.student.management.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.controller.handler.ExHandler;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.MainService;

/**
 *  受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class MainController {

  private MainService service;

  @Autowired
  public MainController(MainService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です。
   * 全件検索を行うので、条件指定は行わないません。
   *
   * @return　受講生詳細一覧（全件）
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   *　受講生詳細検索です。
   *　IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id　受講生ID
   * @return　受講生詳細
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(
      @PathVariable @Pattern(regexp = "^\\d+$") String id) {
    return service.searchStudentId(id);
  }

  /**
   * 受講生の登録を行います。
   *
   * @param studentDetail　受講生詳細
   * @return　実行結果
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@Valid @RequestBody StudentDetail studentDetail) {
    StudentDetail responseDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseDetail);
  }

  /**
   * 受講生詳細の更新を行います。
   * キャンセルフラッグの更新もここで行います。（論理削除）
   *
   * @param studentDetail　受講生詳細
   * @return　実行結果
   */
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@Valid @RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

}
