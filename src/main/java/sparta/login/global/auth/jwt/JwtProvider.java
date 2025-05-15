package sparta.login.global.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

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
		String accessToken = createToken(new HashMap<>(), getExpireDateAccessToken());
		String refreshToken = createToken(new HashMap<>(), getExpireDateRefreshToekn());
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

	public Claims getClaims(String token) {
		return Jwts.parser()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Date getExpireDateAccessToken() {
		return new Date(System.currentTimeMillis() + accessExpireMillis);
	}

	public Date getExpireDateRefreshToekn() {
		return new Date(System.currentTimeMillis() + refreshExpireMillis);
	}
}
