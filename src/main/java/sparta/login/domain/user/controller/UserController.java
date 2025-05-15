package sparta.login.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import sparta.login.domain.user.dto.UserAuthSettingResponseDto;
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

	@PatchMapping("admin/users/{userId}/roles")
	public ResponseEntity<UserAuthSettingResponseDto> authSetting(@PathVariable Long userId, HttpServletRequest request) {

		String username = (String)request.getAttribute("username");
		String roles = (String)request.getAttribute("roles");

		return ResponseEntity.ok(userService.authSetting(userId));
	}
}
