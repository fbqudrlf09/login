package sparta.login.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sparta.login.domain.user.dto.UserRegisterDto;
import sparta.login.domain.user.dto.UserSingUpResponseDto;
import sparta.login.domain.user.entity.User;
import sparta.login.domain.user.repository.UserRepository;
import sparta.login.global.config.PasswordEncoder;
import sparta.login.global.exception.BadValueException;
import sparta.login.global.exception.ExceptionEnum;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public UserSingUpResponseDto registerUser(UserRegisterDto userRegisterDto) {

		// 중복 체크
		if (userRepository.existsByUsername(userRegisterDto.getUsername())) {
			throw new BadValueException(ExceptionEnum.USER_ALREADY_EXISTS);
		}

		// 비밀번호 암호화
		String encodePassword = passwordEncoder.encode(userRegisterDto.getPassword());

		// user repository에 저장
		User user = userRegisterDto.toEntity(encodePassword);
		userRepository.save(user);

		return UserSingUpResponseDto.builder()
			.username(user.getUsername())
			.nickname(user.getNickname())
			.roles(List.of(user.getRole()))
			.build();
	}
}
