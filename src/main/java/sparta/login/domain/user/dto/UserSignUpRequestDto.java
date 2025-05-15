package sparta.login.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import sparta.login.domain.user.entity.Role;
import sparta.login.domain.user.entity.User;

@Getter
public class UserSignUpRequestDto {

	@Schema(description = "사용자 이름")
	private String username;

	@Schema(description = "사용자 비밀번호")
	private String password;

	@Schema(description = "사용자 별명")
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
