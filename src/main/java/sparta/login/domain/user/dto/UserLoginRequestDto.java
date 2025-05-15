package sparta.login.domain.user.dto;

import lombok.Getter;

@Getter
public class UserLoginRequestDto {

	private String username;

	private String password;

	public UserLoginRequestDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UserLoginRequestDto() {
	}
}
