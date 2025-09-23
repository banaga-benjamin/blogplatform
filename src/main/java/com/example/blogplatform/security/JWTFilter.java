package com.example.blogplatform.security;

import org.springframework.stereotype.Component;

import java.io.IOException;
import jakarta.servlet.ServletException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil util;
    private final UserDetailsService service;

    public JWTFilter(UserDetailsService service, JWTUtil util) {
        this.util = util;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter)
            throws ServletException, IOException {
        
        String path = request.getServletPath( );
        if (path.startsWith("/apis/auth")) {
            filter.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filter.doFilter(request, response);
            return;
        }
        
        String token = authHeader.substring(7);
        String username = util.extractUsername(token);
        if (username == null) {
            filter.doFilter(request, response);
            return;
        }

        UserDetails user = service.loadUserByUsername(username);
        if (util.validateToken(token, user.getUsername( ))) {

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities( ));
            SecurityContextHolder.getContext( ).setAuthentication(authToken);
        }
        filter.doFilter(request, response);
    }
}
