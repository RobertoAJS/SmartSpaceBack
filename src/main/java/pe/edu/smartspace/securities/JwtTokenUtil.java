package pe.edu.smartspace.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    // 5 horas
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000L;

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey signingKey;

    @PostConstruct
    private void init() {
        byte[] keyBytes;

        try {
            keyBytes = Base64.getDecoder().decode(secret);
        } catch (IllegalArgumentException ex) {
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }

        if (keyBytes.length < 64) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-512");
                keyBytes = md.digest(keyBytes);
            } catch (NoSuchAlgorithmException e) {
                byte[] padded = new byte[64];
                System.arraycopy(keyBytes, 0, padded, 0, Math.min(keyBytes.length, 64));
                keyBytes = padded;
            }
        }

        signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // ============================
    //   GETTERS DE CLAIMS
    // ============================

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> resolver) {
        return resolver.apply(getAllClaimsFromToken(token));
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    // ============================
    //   GENERACIÓN DE TOKEN
    // ============================

    public String generateToken(UserDetails userDetails, Long userId) {
        Map<String,Object> claims = new HashMap<>();

        // roles en formato ["ROLE_ADMIN", "ROLE_CLIENTE"]
        claims.put("roles",
                userDetails.getAuthorities().stream()
                        .map(a -> a.getAuthority().toUpperCase())
                        .toList()
        );

        // ID de usuario (lo leerá Angular)
        claims.put("userId", userId);

        return doGenerateToken(claims, userDetails.getUsername());
    }

    // solo por compatibilidad si alguien usa Authentication
    public String generateToken(Authentication auth) {
        UserDetails ud = (UserDetails) auth.getPrincipal();
        return generateToken(ud, 0L);
    }

    private String doGenerateToken(Map<String,Object> claims, String subject) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(JWT_TOKEN_VALIDITY)))
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // ============================
    //   VALIDACIÓN
    // ============================

    public boolean validateToken(String token, UserDetails user) {
        return user.getUsername().equals(getUsernameFromToken(token))
                && !isTokenExpired(token);
    }

    public boolean isTokenValid(String token) {
        try {
            getAllClaimsFromToken(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}