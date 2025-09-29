package com.baontin.jobportal.services;

import com.baontin.jobportal.entity.Users;
import com.baontin.jobportal.repository.UsersRepository;
import com.baontin.jobportal.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// UserDetailsService is the bridge between Spring Security and your database
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // Tell Spring Security (SC) how to retrieve a user from the db
    // If found → wrap into CustomUserDetails. >< throw UsernameNotFoundException
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(username).orElseThrow(() -> new
                UsernameNotFoundException("Could not found user"));
        return new CustomUserDetails(user);
    }
}
