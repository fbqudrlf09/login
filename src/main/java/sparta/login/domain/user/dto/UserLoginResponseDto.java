package sparta.login.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserLoginResponseDto {

	@Schema(description = "엑세스 토큰")
	String token;

	public UserLoginResponseDto(String token) {
		this.token = token;
	}
}
