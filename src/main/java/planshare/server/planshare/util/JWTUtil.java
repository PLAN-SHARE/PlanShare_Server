package planshare.server.planshare.util;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JWTUtil {
    public String makeJWT(Long id, String email) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()))
                .claim("id", id)
                .claim("email", email)
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
    }

    public Long getJwtId(String JWT) {
        return Long.parseLong(Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(JWT)
                .getBody().get("id").toString());
    }

    public String getJwtEmail(String JWT) {
        return (String) Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(JWT)
                .getBody().get("email");
    }
}