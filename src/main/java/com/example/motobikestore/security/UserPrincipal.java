package com.example.motobikestore.security;

import com.example.motobikestore.entity.Images;
import com.example.motobikestore.entity.Users;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
public class UserPrincipal implements UserDetails {

    private final UUID userID;
    private final String email;
    private final String password;
    private final Boolean active;
    private final String firstName;
    private final String lastName;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private final Images avatar;

    public static UserPrincipal create(Users users) {
        String userRole = users.getRoles().iterator().next().name();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userRole));
        return new UserPrincipal(users.getUserID(), users.getEmail(), users.getPassword(),users.isActive(),users.getFirstName(),users.getLastName(), authorities,users.getAvatar());
    }

    public static UserPrincipal create(Users users, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(users);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public String getAvatarUrl(){
        return avatar.getImagePath();
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
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
        return active;
    }
}
