package org.jupi.springtoken;

public interface TokenUserDetailsService {

    /**
     * Fetch user by token.
     *
     * @param token Token, ie. a unique ID.
     * @return {@link org.jupi.springtoken.User} object.
     */
    User user(String token);


    /**
     * Create a new Token associated with some data about a user.
     *
     * @param user {@link org.jupi.springtoken.User} object to contain user-related data.
     * @return Token, ie. a unique ID.
     */
    String createToken(User user);

    /**
     * Invalidate/delete a token.<br/>
     * A token can be used only once, for a single authentication, for a limited amount of time after generation.
     *
     * @param token Token to be invalidated.
     */
    void invalidate(String token);
}
