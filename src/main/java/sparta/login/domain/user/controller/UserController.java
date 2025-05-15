package sparta.login.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sparta.login.domain.user.dto.UserRegisterDto;
import sparta.login.domain.user.service.Userservice;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final Userservice userService;

	@PostMapping("/register")
	public ResponseEntity<UserRegisterDto> register(@RequestMapping UserRegisterDto userRegisterDto) {
		return ResponseEntity.ok(userService.registerUser(userRegisterDto));
	}
}
