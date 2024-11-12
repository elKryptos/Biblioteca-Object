package it.objectmethod.biblioteca.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.objectmethod.biblioteca.enums.RuoloPersona;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtToken {

    @Value("${jwt.secret.key}")
    private String secretKey;
    
    public String tokenGenerator(String name, String email, String telefono, RuoloPersona ruoloPersona) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
                    SignatureAlgorithm.HS256.getJcaName());
            return Jwts.builder()
                    .claim("name", name)
                    .claim("email", email)
                    .claim("telefono", telefono)
                    .claim("ruolo", ruoloPersona.name())
                    .setIssuedAt(new Date())
                    .setExpiration(Date.from(Instant.now().plus(2, ChronoUnit.MINUTES)))
                    .signWith(secretKeySpec)
                    .compact();
        } catch (Exception e) {
            System.err.println("Errore nella creazione del Token" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Jws<Claims> verifyToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
                    SignatureAlgorithm.HS256.getJcaName());
            return Jwts.parser()
                    .setSigningKey(secretKeySpec)
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            System.err.println("Token verification failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
