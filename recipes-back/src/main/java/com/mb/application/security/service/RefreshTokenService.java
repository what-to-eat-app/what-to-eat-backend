package com.mb.application.security.service;

import com.mb.application.entity.TokenType;
import com.mb.application.security.exception.TokenException;
import com.mb.application.security.entity.RefreshTokenEntity;
import com.mb.application.security.entity.UserEntity;
import com.mb.application.security.repository.RefreshTokenRepository;
import com.mb.application.security.repository.UserRepository;
import com.mb.application.security.request.RefreshTokenRequest;
import com.mb.application.security.response.RefreshTokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private JwtService jwtService;
    private final String jwtCookieName = "SESSION_ID";

    public RefreshTokenEntity createRefreshToken(Integer id) {

        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .revoked(false)
                .user(user)
                .token(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
                .expiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshTokenResponse generateNewToken(RefreshTokenRequest refreshTokenRequest) {

        UserEntity user = refreshTokenRepository.findByToken(refreshTokenRequest.getRefreshToken())
                .map(this::verifyExpiration)
                .map(RefreshTokenEntity::getUser)
                .orElseThrow(() -> new TokenException(refreshTokenRequest.getRefreshToken(), "Refresh token does not exist."));

        String token = jwtService.generateToken(user);

        return RefreshTokenResponse.builder()
                .accessToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .tokenType(TokenType.BEARER.name())
                .build();
    }

    private RefreshTokenEntity verifyExpiration(RefreshTokenEntity refreshTokenEntity) {
        if( refreshTokenEntity == null) {
            log.error("Token is null");
            throw new TokenException(null, "Token is null");
        }
        if (refreshTokenEntity.getExpiryDate().before(new Date())) {
            refreshTokenRepository.delete(refreshTokenEntity);
            throw new TokenException(refreshTokenEntity.getToken(),
                    "Refresh Token was expired. Please make a new authentication request");
        }
        return refreshTokenEntity;
    }

    public ResponseCookie generateRefreshJwtCookie(String token) {
        return ResponseCookie.from(jwtCookieName, token)
                .path("/")
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
    }

    public String getRefreshJwtFromCookies(HttpServletRequest request) {
        String jwt = null;
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie != null) {
            jwt = cookie.getValue();
        }
        return jwt;
    }

    public ResponseCookie cleanRefreshJwtCookie() {
        return ResponseCookie.from(jwtCookieName, "")
                .path("/")
                .build();
    }
    public void deleteByToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
    }
}
