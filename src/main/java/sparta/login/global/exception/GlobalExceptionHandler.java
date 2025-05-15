package sparta.login.global.exception;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadValueException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(BadValueException ex) {
		ExceptionEnum error = ex.getExceptionType();
		ErrorResponse response = new ErrorResponse(error.getCode(), error.getMessage());

		return ResponseEntity.status(error.getStatus()).body(response);
	}
}
