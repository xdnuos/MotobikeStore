package com.example.motobikestore.security;

import com.example.motobikestore.entity.Users;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
public class UserPrincipal implements UserDetails {

    private final UUID uuid;
    private final String email;
    private final String password;
    private final Boolean active;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public static UserPrincipal create(Users users) {
        String userRole = users.getRoles().iterator().next().name();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userRole));
        return new UserPrincipal(users.getUserID(), users.getEmail(), users.getPassword(),users.isActive(), authorities);
    }

    public static UserPrincipal create(Users users, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(users);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
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
