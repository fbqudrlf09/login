package sparta.login.domain.user.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import sparta.login.domain.user.entity.Role;

@Getter
public class UserSingUpResponseDto {

	@Schema(description = "사용자 이름")
    private String username;

	@Schema(description = "사용자 별명")
	private String nickname;

	@Schema(description = "사용자 권한")
	private List<Role> roles;

	@Builder
	public UserSingUpResponseDto(String username, String nickname, Role roles) {
		this.username = username;
		this.nickname = nickname;
		this.roles = List.of(roles);
	}
}
