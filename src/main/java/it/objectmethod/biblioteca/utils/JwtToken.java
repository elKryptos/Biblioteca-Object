package it.objectmethod.biblioteca.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Properties;

public class JwtToken {

    public static PrivateKey getPrivateKey() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("key.properties"));
            String privateKey = props.getProperty("private");
            PemReader reader = new PemReader(new FileReader(privateKey));
            PemObject pemObject = reader.readPemObject();
            byte[] content = pemObject.getContent();

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(content);
            PrivateKey pk = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
            return pk;
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey getPublicKey() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("key.properties"));
            String publicKey = props.getProperty("public");
            PemReader reader = new PemReader(new FileReader(publicKey));
            PemObject pemObject = reader.readPemObject();
            byte[] content = pemObject.getContent();

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(content);
            PublicKey pk = KeyFactory.getInstance("RSA").generatePublic(keySpec);
            return pk;
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String tokenGenerator(String name, String email, String telefono) {
        try {
            return Jwts.builder()
                    .claim("name", name)
                    .claim("email", email)
                    .claim("telefono", telefono)
                    .setIssuedAt(new Date())
                    .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
                    .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                    .compact();
        } catch (Exception e) {
            System.err.println("Errore nella creazione del Token" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Jws<Claims> verifyToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        try {
            return Jwts.parser()
                    .setSigningKey(getPublicKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            System.err.println("Token verification failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
