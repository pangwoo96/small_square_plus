package com.smallsquare_plus.modules.user.infrastructure.jwt;

import com.smallsquare_plus.modules.user.domain.enums.Role;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private SecretKey secretKey;
    private final JwtParser jwtParser;

    private static final String USERNAME = "username";
    private static final String TYPE = "type";
    private static final String ROLE = "role";

    @Value("${JWT_ACCESS_EXPIRATION}")
    private Long accessTokenExpiration;

    @Value("${JWT_REFRESH_EXPIRATION}")
    private Long refreshTokenExpiration;

    public JwtProvider(@Value("${JWT_SECRET_KEY}") String secret) {
        System.out.println("JWT_SECRET_KEY value = " + secret);
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName()
        );
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    public String createToken(Long userId, String username, Role role, Long expiredMs, String type) {
        Date now = new Date();
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim(USERNAME, username)
                .claim(TYPE, type)
                .claim(ROLE, role)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public String createAccessToken(Long userId, String username, Role role) {
        return createToken(userId, username, role, accessTokenExpiration, "accessToken");
    }

    public String createRefreshToken(Long userId, String username, Role role) {
        return createToken(userId, username, role, refreshTokenExpiration, "refreshToken");
    }

}
