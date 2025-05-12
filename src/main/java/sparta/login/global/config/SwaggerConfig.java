package sparta.login.global.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	public OpenAPI openAPI(){
		return new OpenAPI()
			.info(new Info()
				.title("바로인턴12기_류병길_과제")
				.description("로그인 API")
				.version("1.0.0"));
	}

}
