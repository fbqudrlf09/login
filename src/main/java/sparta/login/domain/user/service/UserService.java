package sparta.login.domain.user.service;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import sparta.login.domain.user.dto.UserAuthSettingResponseDto;
import sparta.login.domain.user.dto.UserLoginRequestDto;
import sparta.login.domain.user.dto.UserLoginResponseDto;
import sparta.login.domain.user.dto.UserSignUpRequestDto;
import sparta.login.domain.user.dto.UserSingUpResponseDto;
import sparta.login.domain.user.entity.Role;
import sparta.login.domain.user.entity.User;
import sparta.login.domain.user.repository.UserRepository;
import sparta.login.global.auth.jwt.Jwt;
import sparta.login.global.auth.jwt.JwtProvider;
import sparta.login.global.config.PasswordEncoder;
import sparta.login.global.exception.BadValueException;
import sparta.login.global.exception.ExceptionEnum;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	public UserSingUpResponseDto registerUser(UserSignUpRequestDto userRegisterDto) {

		// 중복 체크
		if (userRepository.existsByUsername(userRegisterDto.getUsername())) {
			throw new BadValueException(ExceptionEnum.USER_ALREADY_EXISTS);
		}

		// 비밀번호 암호화
		String encodePassword = passwordEncoder.encode(userRegisterDto.getPassword());

		// user repository에 저장
		User user = userRegisterDto.toEntity(encodePassword, Role.USER);
		userRepository.save(user);

		return UserSingUpResponseDto.builder()
			.username(user.getUsername())
			.nickname(user.getNickname())
			.roles(user.getRole())
			.build();
	}

	public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) {
		// 유저 조회
		User user = getUser(userLoginRequestDto);

		// 비밀번호 검증
		checkingPassword(userLoginRequestDto, user);

		// jwt 토큰 생성
		Map<String, Object> claims = Map.of(
			"userId", user.getId(),
			"username", user.getUsername(),
			"roles", user.getRole()
		);

		Jwt jwt = jwtProvider.createJwt(claims);

		return new UserLoginResponseDto(jwt.getAccessToken());
	}

	@Transactional
	public UserAuthSettingResponseDto authSetting(Long userId) {

		User user = getUser(userId);

		user.setRole(Role.ADMIN);

		return UserAuthSettingResponseDto.builder()
			.username(user.getUsername())
			.nickname(user.getNickname())
			.roles(List.of(user.getRole()))
			.build();
	}

	private void checkingPassword(UserLoginRequestDto userLoginRequestDto, User user) {
		if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
			throw new BadValueException(ExceptionEnum.INVALID_CREDENTIALS);
		}
	}

	private User getUser(UserLoginRequestDto userLoginRequestDto) {
		return userRepository.findByUsername(userLoginRequestDto.getUsername())
			.orElseThrow(() -> new BadValueException(ExceptionEnum.INVALID_CREDENTIALS));
	}

	private User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new BadValueException(ExceptionEnum.NOT_FOUND));
	}
}
