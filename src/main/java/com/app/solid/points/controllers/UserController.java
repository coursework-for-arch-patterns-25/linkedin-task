package com.app.solid.points.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/")
    public Optional<String> getUserName(@AuthenticationPrincipal OAuth2User oauth2User){
        if(oauth2User != null){
            return Optional.ofNullable(oauth2User.getAttribute("name"));
        }

        return Optional.of("");
    }
}
