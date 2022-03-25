package planshare.server.planshare.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;


@RequiredArgsConstructor
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String key;

    private final CustomUserDetailsService customUserDetailsService;

    // key Base64로 인코딩
    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    // jwt token 생성
    public String createJWT(Long id, String email) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now) // 발급시간
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(60).toMillis())) // 만료시간
                .claim("id", id)
                .claim("email", email)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // JWT 토큰에서 인증(유저) 정보 조회
    public Authentication getAuthentication(String accessToken) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getJwtEmail(accessToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 유저정보 parsing
    public String getJwtId(String token) {
        return (String) Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody().get("id").toString();
    }

    public String getJwtEmail(String token) {
        return (String) Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody().get("email");
    }

    // header에서 token 값 추출
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return token.substring("Bearer ".length());
    }

    // 토큰 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
