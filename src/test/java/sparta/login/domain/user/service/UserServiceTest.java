package sparta.login.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtProvider jwtProvider;

	@Test
	public void 회원가입_정상_검증() {
		// given
		UserSignUpRequestDto requestDto = new UserSignUpRequestDto("username", "password", "nickname");
		when(userRepository.existsByUsername(anyString())).thenReturn(false);
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		User user = new User("username", "encodedPassword", "nickname", Role.USER);
		when(userRepository.save(any(User.class))).thenReturn(user);

		// when
		UserSingUpResponseDto responseDto = userService.registerUser(requestDto);

		// then
		assertEquals("username", responseDto.getUsername());
		assertEquals("nickname", responseDto.getNickname());
		assertTrue(responseDto.getRoles().contains(Role.USER));
	}

	@Test
	public void 회원가입_중복닉네임_검증() {
		// given
		UserSignUpRequestDto requestDto = new UserSignUpRequestDto("username", "password", "nickname");
		when(userRepository.existsByUsername(anyString())).thenReturn(true);

		//when
		assertThrows(BadValueException.class, () -> {
			userService.registerUser(requestDto);
		});
	}

	@Test
	void 로그인_정상_검증(){
	    // given
		UserLoginRequestDto requestDto = new UserLoginRequestDto("username", "password");
		User user = new User("username", "encodedPassword", "nickname", Role.USER);
		user.setId(1L);

		when(userRepository.findByUsername("username")).thenReturn((Optional.of(user)));
		when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

		Jwt jwt = new Jwt("accessToken", "refreshToken");
		when(jwtProvider.createJwt(any(Map.class))).thenReturn(jwt);

		// when
		UserLoginResponseDto responseDto = userService.loginUser(requestDto);

		// then
		assertEquals("accessToken", responseDto.getToken());

	}

	@Test
	void 로그인_비밀번호_불일치(){
	    // given
		UserLoginRequestDto requestDto = new UserLoginRequestDto("username", "password");
		User user = new User("username", "encodedPassword", "nickname", Role.USER);
		user.setId(1L);

		when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(false);

		// when //then
		assertThrows(BadValueException.class, () -> userService.loginUser(requestDto));
	}

	@Test
	void 로그인_유저조회_실패(){
	    // given
		UserLoginRequestDto requestDto = new UserLoginRequestDto("username", "password");
		when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

		// when //then
		assertThrows(BadValueException.class, () -> userService.loginUser(requestDto));
	}


}