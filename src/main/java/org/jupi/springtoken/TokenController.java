package org.jupi.springtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(SecurityConfig.CREATE_TOKEN_URL)
    public String createTokenGet(Model model) {
        model.addAttribute("user", new User("Moriczka", 25));
        return SecurityConfig.CREATE_TOKEN_URL;
    }

    @RequestMapping(value = SecurityConfig.CREATE_TOKEN_URL, method = RequestMethod.POST)
    public String createTokenPost(@ModelAttribute User user, Model model) {
        model.addAttribute("token", tokenService.createToken(user));
        return SecurityConfig.CREATE_TOKEN_URL;
    }

    @RequestMapping(SecurityConfig.ACCESS_DENIED_URL)
    public String enterTokenGet(Model model) {
        return SecurityConfig.ACCESS_DENIED_URL;
    }

    @RequestMapping(value = SecurityConfig.ACCESS_DENIED_URL, method = RequestMethod.POST)
    public String enterTokenPost(@RequestParam String token) {
        SecurityContextHolder.getContext().setAuthentication(new PreAuthenticatedAuthenticationToken(token, token));
        return "redirect:/";
    }

    @RequestMapping
    public String user(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user";
    }
}
