package com.app.solid;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;
import java.util.Date;

public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oauth2User.getAttributes().get("emailAddress");

        System.out.println("Authentication successful: " + authentication.getName());

        request.getSession().setAttribute("email", email);
        request.getSession().setAttribute("name", oauth2User.getAttributes().get("name"));

        response.setStatus(200);
        response.sendRedirect("/");
    }
}
