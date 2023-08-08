package com.example.motobikestore.security;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Base64;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class JwtTokenValidatorTest {

    @Mock
    private Jws<Claims> claimsJws;

    @Mock
    private Claims claims;

    @InjectMocks
    private JwtProvider jwtTokenValidator;

    private String secretKey = "dejavu";
    private final long validityInMilliseconds = 604800; // 1 hour in seconds

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256,secretKey )
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (SignatureException e) {

        }
        return false;
    }
    @Test
    public void testValidToken() {
        String token = createToken("xdnuos@gmail.com","ADMIN");
        boolean isValid = validateToken(token);

        assertTrue(isValid);
    }

    @Test
    public void testExpiredToken() {
        when(claimsJws.getBody()).thenReturn(claims);
        when(claims.getExpiration()).thenReturn(new Date(System.currentTimeMillis() - 1000)); // Expired token

        boolean isValid = jwtTokenValidator.validateToken("your_expired_jwt_token");

        assertFalse(isValid);
    }

    @Test
    public void testInvalidToken() {
        when(claimsJws.getBody()).thenThrow(MalformedJwtException.class); // Simulate MalformedJwtException

        boolean isValid = jwtTokenValidator.validateToken("your_invalid_jwt_token");

        assertFalse(isValid);
    }

    // Add more tests for other exception cases (ExpiredJwtException, SignatureException, UnsupportedJwtException)

    // ...
}
