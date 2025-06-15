package org.consolidate.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.util.Collections;
import java.util.Map;

@RestController
public class LinkedInAuthController {

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            String firstName = principal.getAttribute("localizedFirstName");
            String lastName = principal.getAttribute("localizedLastName");
            return Collections.singletonMap("name", firstName + " " + lastName);
        }
        return Collections.emptyMap();
    }
} 