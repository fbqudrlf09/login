package sparta.login.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import sparta.login.domain.user.dto.UserAuthSettingResponseDto;
import sparta.login.domain.user.dto.UserLoginRequestDto;
import sparta.login.domain.user.dto.UserLoginResponseDto;
import sparta.login.domain.user.dto.UserSignUpRequestDto;
import sparta.login.domain.user.dto.UserSingUpResponseDto;
import sparta.login.domain.user.service.UserService;
import sparta.login.global.exception.ErrorResponse;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

	private final UserService userService;

	@Operation(summary = "회원가입", description = "사용자 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSingUpResponseDto.class))),
		@ApiResponse(responseCode = "400", description = "닉네임 중복", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))}
	)
	@PostMapping("/signup")
	public ResponseEntity<UserSingUpResponseDto> register(@RequestBody UserSignUpRequestDto userRegisterDto) {

		return ResponseEntity.ok(userService.registerUser(userRegisterDto));
	}


	@Operation(summary = "로그인", description = "사용자 로그인")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserLoginResponseDto.class))),
		@ApiResponse(responseCode = "400", description = "로그인 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))}
		)
	@PostMapping("/login")
	public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
		return ResponseEntity.ok(userService.loginUser(userLoginRequestDto));
	}


	@Operation(summary = "권한 설정", description = "사용자 권한 설정")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "권한 설정 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAuthSettingResponseDto.class))),
		@ApiResponse(responseCode = "400", description = "권한 설정 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))}
	)
	@PatchMapping("admin/users/{userId}/roles")
	public ResponseEntity<UserAuthSettingResponseDto> authSetting(@PathVariable Long userId, HttpServletRequest request) {

		String username = (String)request.getAttribute("username");
		String roles = (String)request.getAttribute("roles");

		return ResponseEntity.ok(userService.authSetting(userId));
	}
}
