package sparta.login.domain.user.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import sparta.login.domain.user.entity.Role;

@Getter
public class UserSingUpResponseDto {

    private String username;
	private String nickname;
	private List<Role> roles;

	@Builder
	public UserSingUpResponseDto(String username, String nickname, List<Role> roles) {
		this.username = username;
		this.nickname = nickname;
		this.roles = roles;
	}
}
