package sparta.login.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ErrorResponse {

	private final ErrorDetail error;

	public ErrorResponse(String code, String message) {
		this.error = new ErrorDetail(code, message);
	}

	@Getter
	static class ErrorDetail{

		@Schema(description = "에러 코드명")
		private final String code;

		@Schema(description = "에러 설명")
		private final String message;

		public ErrorDetail(String code, String message) {
			this.code = code;
			this.message = message;
		}
	}
}
