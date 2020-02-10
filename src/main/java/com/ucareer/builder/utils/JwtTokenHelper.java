
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class JwtTokenHelper {
    private String secret;
    private Long expireHours;

    public JwtTokenHelper(String token, Long expireHours) {
        this.secret = token;
        this.expireHours = expireHours;
    }

    public String creatToken(User user) {
        Date now = new Date();
        Long expireInMilis = TimeUnit.HOURS.toMillis(expireHours);
        Date expiredAt = new Date(expireInMilis + now.getTime());

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String verifyToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            return username;
        } catch (Exception e) {
            return null;
        }
    }
}