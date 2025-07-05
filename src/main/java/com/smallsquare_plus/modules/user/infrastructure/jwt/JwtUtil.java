package com.smallsquare_plus.modules.user.infrastructure.jwt;

import com.smallsquare_plus.modules.user.exception.exception.UserException;
import com.smallsquare_plus.modules.user.infrastructure.redis.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.smallsquare_plus.modules.user.exception.errorcode.UserErrorCode.REFRESH_TOKEN_EXPIRED;

@Component
public class JwtUtil {

    private final JwtParser jwtParser;
    private final SecretKey secretKey;
    private final RedisService redisService;

    public JwtUtil(@Value("${JWT_SECRET_KEY}") String secret, RedisService redisService) {
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName()
        );
        this.redisService = redisService;
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    // 토큰 전체 Claims 추출
    public Claims parseAllClaims(String token) {
        return jwtParser.parseSignedClaims(token).getBody();
    }

    public Long getUserId(String token) {
        return Long.parseLong(jwtParser.parseSignedClaims(token).getPayload().getSubject());
    }

    // Username 추출
    public String getUsername(String token) {
        return parseAllClaims(token).get("username", String.class);
    }

    public String getRole(String token) {
        return parseAllClaims(token).get("role", String.class);
    }

    // 토큰 발급시간(IssuedAt) 추출
    public Date getIssuedAt(String token) {
        return parseAllClaims(token).getIssuedAt();
    }

    // 토큰 만료시간(Expiration) 추출
    public Date getExpiration(String token) {
        return parseAllClaims(token).getExpiration();
    }

    // 현재 시간 기준 남은 만료시간(ms)
    public long getRemainingExpirationMillis(String token) {
        long now = System.currentTimeMillis();
        return getExpiration(token).getTime() - now;
    }

    // 토큰 만료 여부 확인
    public boolean isExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 토큰 재발급 시 기존 폐기된 리프레시 토큰이 사용되었는지 확인하는 로직
     * @param refreshToken
     */
    public void validateRefreshTokenBlackList(String refreshToken) {
        String key = redisService.get("blacklist:refresh:" + refreshToken);
        if (StringUtils.hasText(key)) {
            throw new UserException(REFRESH_TOKEN_EXPIRED);
        }
    }
}
