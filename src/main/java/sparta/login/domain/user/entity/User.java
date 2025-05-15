package sparta.login.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickname;

	@Enumerated(value = EnumType.STRING)
	private Role role = Role.USER;

	@Builder
	public User(String username, String password, String nickname) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}

	public User() {
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
