package sparta.login.global.exception;

import lombok.Getter;

@Getter
public class BadValueException extends RuntimeException {

	private final ExceptionEnum exceptionType;

    public BadValueException(ExceptionEnum exceptionType) {
			super(exceptionType.getMessage());
			this.exceptionType = exceptionType;
		}
}
