package com.example.motobikestore.security;

import com.example.motobikestore.entity.User;
import com.example.motobikestore.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.motobikestore.constants.ErrorMessage.USER_NOT_FOUND;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
//        if (account.getActivationCode() != null) {
//            throw new LockedException("Email not activated");
//        }
        return UserPrincipal.create(user);
    }
}
