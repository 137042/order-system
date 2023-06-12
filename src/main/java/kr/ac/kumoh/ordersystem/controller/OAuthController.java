package kr.ac.kumoh.ordersystem.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class OAuthController {

//    @GetMapping("/signup")
//    public String signupForm(@ModelAttribute("signupForm") )

    @GetMapping("/oauth/loginInfo")
    public String oauthLoginInfo(Model model, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:3005/loginOk")
                .queryParam("member_id", attributes.get("member_id"))
                .queryParam("email", attributes.get("email"))
                .build().toUriString();

        return "redirect:" + redirectUrl;
    }
}