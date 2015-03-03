package org.jupi.springtoken;

public interface TokenService {

    boolean validate(String token);

    User user(String token);

    String createToken(User user);

    void invalidate(String token);
}
