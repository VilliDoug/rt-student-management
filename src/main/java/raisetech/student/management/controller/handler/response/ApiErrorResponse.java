package raisetech.student.management.controller.handler.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "汎用的なAPIエラーのレスポンス構造")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {

    @Schema(description = "エラーメッセージ")
    private String errorResponse;
}


