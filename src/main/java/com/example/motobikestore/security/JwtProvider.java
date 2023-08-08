package com.example.motobikestore.security;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Console;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    @Qualifier("userDetailsServiceImpl") @Lazy
    private final UserDetailsService userDetailsService;

    @Value("${jwt.header}")
    private String authorizationHeader;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

//    public boolean validateToken(String token) {
//        try {
//            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            return !claimsJws.getBody().getExpiration().before(new Date());
//        } catch (JwtException | IllegalArgumentException exception) {
//            System.out.println(exception);
//            throw new JwtAuthenticationException(INVALID_JWT_TOKEN, HttpStatus.UNAUTHORIZED);
//        }
//    }
public boolean validateToken(String token) {
    try {
        System.out.println(token);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return !claimsJws.getBody().getExpiration().before(new Date());
    } catch (SignatureException e) {
        log.info("Invalid JWT signature.");
        log.trace("Invalid JWT signature trace: {}", e);
    } catch (MalformedJwtException e) {
        log.info("Invalid JWT token.");
        log.trace("Invalid JWT token trace: {}", e);
    } catch (ExpiredJwtException e) {
        log.info("Expired JWT token.");
        log.trace("Expired JWT token trace: {}", e);
    } catch (UnsupportedJwtException e) {
        log.info("Unsupported JWT token.");
        log.trace("Unsupported JWT token trace: {}", e);
    } catch (IllegalArgumentException e) {
        log.info("JWT token compact of handler are invalid.");
        log.trace("JWT token compact of handler are invalid trace: {}", e);
    }
    return false;
}

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken (HttpServletRequest httpServletRequest) {
        final String bearerToken = httpServletRequest.getHeader(authorizationHeader);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {return bearerToken.substring(7).replaceAll("\"", ""); } // The part after "Bearer "
        return null;
    }
}
