package ru.miraq.mail.authservice.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.miraq.mail.authservice.security.user.UserDetailsImpl;

import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    @Value("${service.security.jwtSecret}")
    private String jwtSecret;

    @Value("${service.security.jwtExpirationMs}")
    private Duration tokenExpiration;

    public String generateJwtToken(UserDetailsImpl userDetails){
        return generateTokenFromUsername(userDetails.getUsername());
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenExpiration.toMillis()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String getUsername(String token){
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validate(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e){
            log.error("Invalid signature: {}", e.getMessage());
        } catch (MalformedJwtException e){
            log.error("Invalid token: {}", e.getMessage());
        } catch (ExpiredJwtException e){
            log.error("Token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            log.error("Token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e){
            log.error("Claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
