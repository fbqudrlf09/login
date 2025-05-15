package sparta.login.global.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import sparta.login.global.exception.BadValueException;
import sparta.login.global.exception.ExceptionEnum;

@Component
public class JwtProvider {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.access-expire-millis}")
	private long accessExpireMillis;

	@Value("${jwt.refresh-expire-millis}")
	private long refreshExpireMillis;

	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(String.valueOf(secret).getBytes(StandardCharsets.UTF_8));
	}

	public Jwt createJwt(Map<String, Object> claims) {
		String accessToken = createToken(claims, getExpireDateAccessToken());
		String refreshToken = createToken(claims, getExpireDateRefreshToken());
		return Jwt.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public String createToken(Map<String, Object> claims, Date expireDate) {
		return Jwts.builder()
			.setClaims(claims)
			.setExpiration(expireDate)
			.signWith(key)
			.compact();
	}

	// 토큰 검증
	public Claims validateToken(String jwtToken) {
		try {
			Claims claims = Jwts.parser()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(jwtToken)
				.getBody();

			if(claims.getExpiration().before(new Date())) {
				throw new BadValueException(ExceptionEnum.EXPIRE_TOKEN);
			}
			return claims;

		} catch (Exception e) {
			throw new BadValueException(ExceptionEnum.INVALID_TOKEN);
		}
	}

	public Date getExpireDateAccessToken() {
		return new Date(System.currentTimeMillis() + accessExpireMillis);
	}

	public Date getExpireDateRefreshToken() {
		return new Date(System.currentTimeMillis() + refreshExpireMillis);
	}
}
