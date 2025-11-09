package raisetech.student.management.controller.handler.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "バリデーションエラーのレスポンス構造")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {

  @Schema(description = "フィールド名とエラーメッセージのマップ")
  private Map<String, String> validationErrors;

}
