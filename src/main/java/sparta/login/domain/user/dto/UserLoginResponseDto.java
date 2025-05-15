package sparta.login.domain.user.dto;

import lombok.Getter;

@Getter
public class UserLoginResponseDto {

	String token;

	public UserLoginResponseDto(String token) {
		this.token = token;
	}
}
