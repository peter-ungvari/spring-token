package org.jupi.springtoken;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenUserDetailsServiceImpl
        implements TokenUserDetailsService, AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private Map<String, User> tokenUserMap = new HashMap<>();

    @Override
    public User user(String token) {
        return tokenUserMap.get(token);
    }

    @Override
    public String createToken(User user) {
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        tokenUserMap.put(token, user);
        return token;
    }

    @Override
    public void invalidate(String token) {
        tokenUserMap.remove(token);
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken auth) throws UsernameNotFoundException {
        String token = (String) auth.getPrincipal();
        User user = user(token);
        if (user == null) {
            throw new UsernameNotFoundException(token);
        }
        return new UserDetailsWrapper(user);
    }
}
