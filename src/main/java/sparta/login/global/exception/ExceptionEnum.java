package sparta.login.global.exception;

import org.springframework.http.HttpStatus;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;

@Getter
public enum ExceptionEnum {

	USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "USER_ALREADY_EXISTS", "이미 가입된 사용자입니다"),
	INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, "INVAID_CREDENTIALS", "아이디 또는 비밀번호가 올바르지 않습니다"),
	NOT_FOUND(HttpStatus.BAD_REQUEST, "NOT_FOUND", "회원의 정보를 찾을 수 없습니다"),
	EXPIRE_TOKEN(HttpStatus.UNAUTHORIZED, "EXPIRED_TOKEN", "토큰이 만료되었습니다"),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN", "유효하지 않은 인증 토큰입니다"),
	ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "접근 권한이 없습니다");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ExceptionEnum(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
