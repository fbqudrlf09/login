package sparta.login.global.exception;

import org.springframework.http.HttpStatus;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;

@Getter
public enum ExceptionEnum {

	USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "USER_ALREADY_EXISTS", "이미 가입된 사용자입니다");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ExceptionEnum(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
