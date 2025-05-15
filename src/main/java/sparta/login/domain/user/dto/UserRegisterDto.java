package sparta.login.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRegisterDto {

	private String username;

	private String password;

	private String nickname;

	@Builder
	public UserRegisterDto(String username, String password, String nickname) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}
}
