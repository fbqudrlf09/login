package sparta.login.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import sparta.login.global.auth.interceptor.JwtInterceptor;
import sparta.login.global.auth.jwt.JwtProvider;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final JwtProvider jwtProvider;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new JwtInterceptor(jwtProvider))
			.addPathPatterns("/admin/**");
	}
}
