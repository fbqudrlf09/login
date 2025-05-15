package sparta.login.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sparta.login.domain.user.dto.UserLoginRequestDto;
import sparta.login.domain.user.dto.UserLoginResponseDto;
import sparta.login.domain.user.dto.UserSignUpRequestDto;
import sparta.login.domain.user.dto.UserSingUpResponseDto;
import sparta.login.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<UserSingUpResponseDto> register(@RequestBody UserSignUpRequestDto userRegisterDto) {

		return ResponseEntity.ok(userService.registerUser(userRegisterDto));
	}

	@PostMapping("login")
	public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
		return ResponseEntity.ok(userService.loginUser(userLoginRequestDto));
	}
}
