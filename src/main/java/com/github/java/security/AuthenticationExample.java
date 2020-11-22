package com.github.java.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengfei.zhao
 * @date 2020/11/22 12:24
 */
public class AuthenticationExample {
    private static AuthenticationManager am = new SampleAuthenticationManager();

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Please enter your username: ");
            String name = in.readLine();
            System.out.println("Please enter your password: ");
            String password = in.readLine();
            try {
                Authentication request = new UsernamePasswordAuthenticationToken(name, password);
                Authentication result = am.authenticate(request);
                SecurityContextHolder.getContext().setAuthentication(result);
                break;
            } catch (AuthenticationException e) {
                System.out.println("Authentication failed:" + e.getMessage());
            } finally {
                System.out.println("Successfully authenticated. Security context contains:" +
                        SecurityContextHolder.getContext().getAuthentication());
            }


        }
    }
}

class SampleAuthenticationManager implements AuthenticationManager{
    static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getName().equals(authentication.getCredentials())) {
            return new UsernamePasswordAuthenticationToken(authentication.getName(),
                    authentication.getCredentials(), AUTHORITIES);
        }
        throw new BadCredentialsException("Bad Credentials");
    }
}
