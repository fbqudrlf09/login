package sparta.login.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import sparta.login.domain.user.entity.Role;
import sparta.login.domain.user.entity.User;

@Getter
public class UserSignUpRequestDto {

	private String username;

	private String password;

	private String nickname;

	@Builder
	public UserSignUpRequestDto(String username, String password, String nickname) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}

	@Builder
	public User toEntity(String encodedPassword, Role role) {
		return User.builder()
			.username(username)
			.password(encodedPassword)
			.nickname(nickname)
			.role(role)
			.build();
	}
}
