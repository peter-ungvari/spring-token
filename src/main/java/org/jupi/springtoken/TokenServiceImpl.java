package org.jupi.springtoken;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenServiceImpl implements TokenService {

    private Map<String, User> tokenUserMap = new HashMap<>();

    @Override
    public boolean validate(String token) {
        return tokenUserMap.containsKey(token);
    }

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
}
