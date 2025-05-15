package sparta.login.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

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
import sparta.login.domain.user.dto.UserSignUpRequestDto;
import sparta.login.domain.user.dto.UserSingUpResponseDto;
import sparta.login.domain.user.entity.Role;
import sparta.login.domain.user.entity.User;
import sparta.login.domain.user.repository.UserRepository;
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
}