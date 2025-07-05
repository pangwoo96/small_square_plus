package com.smallsquare_plus.modules.user.infrastructure.redis;

import com.smallsquare_plus.modules.user.exception.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.smallsquare_plus.modules.user.exception.errorcode.UserErrorCode.ACCESS_TOKEN_EXPIRED;
import static com.smallsquare_plus.modules.user.exception.errorcode.UserErrorCode.REFRESH_TOKEN_EXPIRED;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    /**
     * 로그아웃 시 토큰을 Redis에 BlackList로 저장
     * @param accessToken
     * @param accessTokenExpireTime
     * @param refreshToken
     * @param refreshTokenExpireTime
     */

    public void saveBlacklist(String accessToken, long accessTokenExpireTime,
                              String refreshToken, long refreshTokenExpireTime) {

        redisTemplate.opsForValue().set(
                "blacklist:access:" + accessToken,
                "logout",
                Duration.ofMillis(accessTokenExpireTime)
        );

        redisTemplate.opsForValue().set(
                "blacklist:refresh:" + refreshToken,
                "logout",
                Duration.ofMillis(refreshTokenExpireTime)
        );
    }

    public void saveRefreshBlackList(String refreshToken, long refreshTokenExpireTime) {
        redisTemplate.opsForValue().set(
                "blacklist:refresh:" + refreshToken,
                "refresh",
                Duration.ofMinutes(refreshTokenExpireTime)
        );
    }

    public void validateAccessToken(String accessToken) {
        if (isBlacklisted(accessToken)) {
            throw new UserException(REFRESH_TOKEN_EXPIRED);
        }
    }

    public void validateRefreshToken(String refreshToken) {
        if (isBlacklisted(refreshToken)) {
            throw new UserException(REFRESH_TOKEN_EXPIRED);
        }
    }

    public boolean isBlacklisted(String token) {
        return redisTemplate.hasKey("blacklist: " + token);
    }

    /**
     * API 호출 시 토큰이 블랙리스트인지 확인하는 메소드
     * @param accessToken
     * @param refreshToken
     */
    public void validateBlacklist(String accessToken, String refreshToken) {
        validateAccessToken(accessToken);
        validateRefreshToken(refreshToken);
    }

    public void set(String key, String value, long expirationSeconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(expirationSeconds));
    }

    // 토큰을 기반으로 Redis에서 값(email) 조회
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Redis에서 해당 key 삭제
    public void delete(String key) {
        redisTemplate.delete(key);
    }

}