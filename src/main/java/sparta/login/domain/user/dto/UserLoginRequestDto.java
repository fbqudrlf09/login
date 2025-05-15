package sparta.login.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserLoginRequestDto {
	@Schema(description = "사용자 이름")
	private String username;

	@Schema(description = "사용자 별명")
	private String password;

	public UserLoginRequestDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UserLoginRequestDto() {
	}
}
