package com.example.blogplatform.services;

import org.springframework.stereotype.Service;

import com.example.blogplatform.models.User;
import com.example.blogplatform.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userrepo;

    public CustomUserDetailsService(UserRepository userrepo) {
        this.userrepo = userrepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userrepo.findByUsername(username).orElseThrow(( ) -> new UsernameNotFoundException("user does not exist"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername( ))
                .password(user.getPassword( ))
                .build( );
    }

}
