package kr.ac.kumoh.ordersystem.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    final String REDIRECT_URL = "http://localhost:8080/login/oauth2/code/google";
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oAuth2User = authentication.getPrincipal();
        response.sendRedirect(UriComponentsBuilder.fromUriString(REDIRECT_URL)
                .queryParam("accessToken", "accessToken")
                .queryParam("refreshToken", "refreshToken")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString());
    }
}
