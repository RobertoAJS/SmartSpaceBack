package pe.edu.smartspace.securities;

import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.nio.charset.StandardCharsets;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import java.security.Key;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.repositories.IUsuarioRepository;

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

    // 5 horas en milisegundos
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000L;

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey signingKey;

    @Autowired
    private IUsuarioRepository usuarioRepository;


    @PostConstruct
    private void init() {
        byte[] keyBytes;
        try {
            // intenta decodificar Base64 si es que la clave fue puesta así
            keyBytes = Base64.getDecoder().decode(secret);
        } catch (IllegalArgumentException ex) {
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        // Asegurar longitud suficiente para HS512 (64 bytes). Si es corta, derivar con SHA-512.
        if (keyBytes.length < 64) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-512");
                keyBytes = md.digest(keyBytes);
            } catch (NoSuchAlgorithmException e) {
                // fallback: pad con ceros (muy improbable que falle)
                byte[] padded = new byte[64];
                System.arraycopy(keyBytes, 0, padded, 0, Math.min(keyBytes.length, 64));
                keyBytes = padded;
            }
        }
        signingKey = Keys.hmacShaKeyFor(keyBytes);
    }


    // ---- helpers para claims ----
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)           // <-- AHORA USA LA MISMA CLAVE
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // ---- generación de token ----
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // 1. Agregar roles (lo que ya tenías)
        claims.put("roles", userDetails.getAuthorities());

        // 2. --- AGREGAR EL ID ---
        // Buscamos el usuario completo en la BD usando el username
        Usuario u = usuarioRepository.findOneByUsername(userDetails.getUsername());

        if (u != null) {
            claims.put("id", u.getIdUsuario());       // <--- AQUÍ GUARDAMOS EL ID
            claims.put("nombre", u.getNombre());      // (Opcional) Guardamos el nombre real
        }
        // ------------------------

        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Instant now = Instant.now();
        Date issuedAt = Date.from(now);
        Date expiration = Date.from(now.plusMillis(JWT_TOKEN_VALIDITY));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // ---- validación ----
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // utilidad: validar sintácticamente (firma + expiración)
    public boolean isTokenValid(String token) {
        try {
            getAllClaimsFromToken(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
