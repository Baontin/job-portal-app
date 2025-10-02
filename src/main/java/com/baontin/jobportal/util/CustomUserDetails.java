package com.baontin.jobportal.util;

import com.baontin.jobportal.entity.Users;
import com.baontin.jobportal.entity.UsersType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Wraps your Users entity and adapts it to Spring Security’s UserDetails interface.
// Think of UserDetails as a wrapper around your Users entity so Spring can read it.
public class CustomUserDetails implements UserDetails {

    private final Users user;

    public CustomUserDetails(Users user) {
        this.user = user;
    }

    // return the roles for this user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UsersType usersType = user.getUserTypeId();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usersType.getUserTypeName()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
