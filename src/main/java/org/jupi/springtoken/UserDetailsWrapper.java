package org.jupi.springtoken;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

public class UserDetailsWrapper extends org.springframework.security.core.userdetails.User implements UserDetails {

    private final User user;

    public UserDetailsWrapper(User user) {
        super(user.getToken(), user.getToken(), new ArrayList<>());
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
