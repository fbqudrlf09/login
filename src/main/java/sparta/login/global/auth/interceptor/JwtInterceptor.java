package sparta.login.global.auth.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import sparta.login.domain.user.entity.Role;
import sparta.login.global.auth.jwt.JwtProvider;
import sparta.login.global.exception.BadValueException;
import sparta.login.global.exception.ExceptionEnum;

@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

	private final JwtProvider jwtProvider;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new BadValueException(ExceptionEnum.INVALID_TOKEN);
		}

		String jwtToken = authHeader.substring(7);

		try {
			Claims claims = jwtProvider.validateToken(jwtToken);
			String username = claims.getSubject();
			String roles = (String)claims.get("roles");

			request.setAttribute("username", username);
			request.setAttribute("roles", roles);

			String requestURI = request.getRequestURI();
			if (requestURI.startsWith("/admin") && !roles.contains(Role.ADMIN.toString())) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				throw new BadValueException(ExceptionEnum.ACCESS_DENIED);
			}

		} catch (BadValueException ex) {
			throw new BadValueException(ex.getExceptionType());
		} catch (Exception e) {
			throw new BadValueException(ExceptionEnum.INVALID_TOKEN);
		}

		return true;
	}
}
