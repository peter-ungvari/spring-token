package org.jupi.springtoken;

public interface TokenUserDetailsService {

    User user(String token);

    String createToken(User user);

    void invalidate(String token);
}
