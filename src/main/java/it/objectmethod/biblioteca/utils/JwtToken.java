package it.objectmethod.biblioteca.utils;

import io.jsonwebtoken.*;
import it.objectmethod.biblioteca.exceptions.NotFoundException;
import it.objectmethod.biblioteca.models.entities.Persona;
import it.objectmethod.biblioteca.repositories.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtToken {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Autowired
    private final PersonaRepository personaRepository;

    public String createToken(Persona persona) {
        personaRepository.findByEmail(persona.getEmail());
        Map<String, String> claims = new HashMap<>();
        claims.put(persona.getNome(), persona.getNome());
        claims.put(persona.getEmail(), persona.getEmail());
        claims.put(persona.getTelefono(), persona.getTelefono());
        claims.put(persona.getRuoloPersona().name(), persona.getRuoloPersona().toString());
        return tokenGenerator(claims);
    }

    private String tokenGenerator(Map<String, String> claims) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
                    SignatureAlgorithm.HS256.getJcaName());
            return Jwts.builder()
                    .claims(claims)
                    .setIssuedAt(new Date())
                    .setExpiration(Date.from(Instant.now().plus(2, ChronoUnit.MINUTES)))
                    .signWith(secretKeySpec)
                    .compact();
        } catch (JwtException e) {
            System.err.println("Errore nella creazione del Token" + e.getMessage());
            throw new RuntimeException("Errore nella creazione del Token\n" + e);
        }
    }

//    public String tokenGenerator(String name, String email, String telefono, RuoloPersona ruoloPersona) {
//        try {
//            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
//                    SignatureAlgorithm.HS256.getJcaName());
//            return Jwts.builder()
//                    .claim("name", name)
//                    .claim("email", email)
//                    .claim("telefono", telefono)
//                    .claim("ruolo", ruoloPersona.name())
//                    .setIssuedAt(new Date())
//                    .setExpiration(Date.from(Instant.now().plus(2, ChronoUnit.MINUTES)))
//                    .signWith(secretKeySpec)
//                    .compact();
//        } catch (Exception e) {
//            System.err.println("Errore nella creazione del Token" + e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }

    public Jws<Claims> allClaimsJws(String token) {
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
        } catch (RuntimeException e) {
            System.err.println("Token verification failed: " + e.getMessage());
            return  null;
        }
    }
}
