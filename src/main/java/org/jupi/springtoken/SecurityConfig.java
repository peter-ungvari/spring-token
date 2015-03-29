package org.jupi.springtoken;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ACCESS_DENIED_URL = "/unauthorized";
    public static final String AUTHORIZE_URL = "/authorize";
    public static final String CREATE_TOKEN_URL = "/create-token";
    public static final String LOGOUT_URL = "/logout";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(ACCESS_DENIED_URL, CREATE_TOKEN_URL, AUTHORIZE_URL).permitAll()
                .anyRequest().authenticated();
        http.logout().addLogoutHandler(logoutHandler(tokenUserDetailsService()))
                .logoutUrl(LOGOUT_URL).logoutSuccessUrl(ACCESS_DENIED_URL);
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
    }

    @Bean
    public LogoutHandler logoutHandler(TokenUserDetailsService tokenUserDetailsService) {
        return (request, response, at) -> {
            UserDetailsWrapper userDetails = (UserDetailsWrapper) at.getPrincipal();
            User user = userDetails.getUser();
            tokenUserDetailsService.invalidate(user.getToken());
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> response.sendRedirect(ACCESS_DENIED_URL);
    }

    @Bean
    public AuthenticationProvider tokenAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(tokenUserDetailsService());
        provider.setThrowExceptionWhenTokenRejected(true);
        return provider;
    }

    @Bean
    public TokenUserDetailsServiceImpl tokenUserDetailsService() {
        return new TokenUserDetailsServiceImpl();
    }
}
