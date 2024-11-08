package com.example.Parking.security;

import com.example.Parking.entity.Role;
import com.example.Parking.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@RequiredArgsConstructor
public class AppUserDetails implements UserDetails {
    private final Users users;
    public Long getId(){
        return users.getId();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return users.getRoles().stream().map(Role::toAuthority).toList();
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getPhoneNumber();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
