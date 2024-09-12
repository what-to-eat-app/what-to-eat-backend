package com.mb.application.security.controller;

import com.mb.application.security.request.AuthenticationRequest;
import com.mb.application.security.request.RefreshTokenRequest;
import com.mb.application.security.request.RegisterRequest;
import com.mb.application.security.response.AuthenticationResponse;
import com.mb.application.security.response.RefreshTokenResponse;
import com.mb.application.security.service.AuthenticationService;
import com.mb.application.security.service.JwtService;
import com.mb.application.security.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
       return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        ResponseCookie jwtCookie = jwtService.generateJwtCookie(authenticationResponse.getAccessToken());
        ResponseCookie refreshTokenCookie = refreshTokenService.generateRefreshJwtCookie(authenticationResponse.getRefreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .body(authenticationResponse);
    }

    @GetMapping("/info")
    public Authentication getAuthentication(@RequestBody AuthenticationRequest request){
        return     authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(refreshTokenService.generateNewToken(request));
    }

    @PostMapping("/refresh-token-cookie")
    public ResponseEntity<RefreshTokenResponse> refreshTokenCookie(HttpServletRequest request) {

        String refreshToken = refreshTokenService.getRefreshJwtFromCookies(request);
        RefreshTokenResponse refreshTokenResponse = refreshTokenService.generateNewToken(new RefreshTokenRequest(refreshToken));

        ResponseCookie newJwtCookie = jwtService.generateJwtCookie(refreshTokenResponse.getAccessToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, newJwtCookie.toString())
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponse> logout(HttpServletRequest request) {

        String refreshToken = refreshTokenService.getRefreshJwtFromCookies(request);
        if (refreshToken != null) {
            refreshTokenService.deleteByToken(refreshToken);
        }
        ResponseCookie jwtCookie = jwtService.cleanJwtCookie();
        ResponseCookie refreshTokenCookie = refreshTokenService.cleanRefreshJwtCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .build();
    }

}
