package raisetech.student.management.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Student {

  @Pattern(regexp = "^\\d+$")
  private String id;

  @Size (min = 2, max = 50, message = "名前は2文字以上50文字以下で入力してください。")
  private String name;

  private String kanaName;
  private String nickname;

  @NotBlank(message = "メールアドレスが必要です。")
  @Email(message = "有効なメールアドレスを入力してください。")
  private String emailAddress;

  private String residence;

  @Min(value = 18, message = "年齢は18歳以上である必要があります。")
  @Max(value = 100, message = "年齢は100歳以下である必要があります。")
  private int age;

  private String gender;
  private String remark;
  private boolean wasDeleted;
  }
