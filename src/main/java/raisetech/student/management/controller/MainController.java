package raisetech.student.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.controller.handler.response.ApiErrorResponse;
import raisetech.student.management.controller.handler.response.ValidationErrorResponse;
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
  @ApiResponses(value = {
      // 200 OK Response (Found)
      @ApiResponse(responseCode = "200", description = "一覧検索成功",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentDetail[].class))),
      // 500 Interval Server Error
      @ApiResponse(responseCode = "500", description = "サーバーエラー (Internal Server Error)",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String emailAddress,
      @RequestParam(required = false) String gender,
      @RequestParam(required = false) String courseName,
      @RequestParam(required = false) String applicationStatus
  ) {
    return service.searchStudentList(name, emailAddress, gender, courseName, applicationStatus);
  }

  /**
   *　受講生詳細検索です。
   *　IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id　受講生ID
   * @return　受講生詳細
   */
  @ApiResponses(value = {
      // 200 OK Response (Found)
      @ApiResponse(responseCode = "200", description = "検索成功",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentDetail.class))),
      // 400 Bad Request
      @ApiResponse(responseCode = "400", description = "IDの形式が不正です",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
      // 404 Not Found
      @ApiResponse(responseCode = "404", description = "受講生が見つかりませんでした。",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
      // 500 Interval Server Error
      @ApiResponse(responseCode = "500", description = "サーバーエラー (Internal Server Error)",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  @Operation(summary = "受講生ID検索", description = "IDに紐づく受講生詳細を検索します。")
  @GetMapping("/student/{id}")
  public ResponseEntity<StudentDetail> getStudent(
      @PathVariable
      @Pattern(regexp = "^\\d+$")
      @Parameter(name = "id", in = ParameterIn.PATH, description = "取得する受講生のID", example = "101")
      String id) {
    StudentDetail studentDetail = service.searchStudentId(id);
    if (studentDetail == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(studentDetail);
  }


  /**
   * 受講生の登録を行います。
   *
   * @param studentDetail　受講生詳細
   * @return　実行結果
   */
  @ApiResponses(value = {
      // 200 OK Response (Success) -- example model
      @ApiResponse(responseCode = "200", description = "登録成功",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentDetail.class))),
      // 400 Bad Request Response (Client Error)
      @ApiResponse(responseCode = "400", description = "リクエスト検証エラー (Bad Request)",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
      // 500 Interval Server Error
      @ApiResponse(responseCode = "500", description = "サーバーエラー (Internal Server Error)",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  @Operation(summary = "受講生登録", description = "受講生を登録します。")
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
  @ApiResponses(value = {
      // 200 Response OK
      @ApiResponse(responseCode = "200", description = "更新成功",
          content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "更新処理が成功しました。"))),
      // 400 Bad Request Response (Client Error)
      @ApiResponse(responseCode = "400", description = "リクエスト検証エラー (Bad Request)",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
      // 500 Interval Server Error
      @ApiResponse(responseCode = "500", description = "サーバーエラー (Internal Server Error)",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  @Operation(summary = "受講生詳細情報更新", description = "受講生詳細情報を更新します。")
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@Valid @RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

}
