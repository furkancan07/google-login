package com.rf.spring_oauth2.security;

import com.rf.spring_oauth2.dto.converter.DtoConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j  // Logger için
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final DtoConverter converter;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User customUser = (CustomOAuth2User) authentication.getPrincipal();

        log.info("OAuth2 authentication successful for user: {}", customUser.getUser().getEmail());

        int oneMonthSeconds = 30 * 24 * 60 * 60;
        ResponseCookie cookie = ResponseCookie.from("login-token", customUser.getToken())
                .httpOnly(true)
                .secure(false) // Proda çıkarsak true yapmam lazım
                .path("/")
                .maxAge(oneMonthSeconds)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        log.info("JWT token set as HttpOnly cookie with name 'login-token'");
        log.debug("Cookie content: {}", cookie.toString());

        String redirectUrl = "http://localhost:5173";
        log.info("Redirecting user to frontend: {}", redirectUrl);

        response.sendRedirect(redirectUrl);
    }
}
