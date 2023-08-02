package com.example.motobikestore.service.Impl;

import com.example.motobikestore.DTO.CaptchaResponse;
import com.example.motobikestore.entity.Users;
import com.example.motobikestore.enums.Role;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.exception.EmailException;
import com.example.motobikestore.exception.PasswordConfirmationException;
import com.example.motobikestore.exception.PasswordException;
import com.example.motobikestore.repository.UserRepository;
import com.example.motobikestore.security.JwtProvider;
import com.example.motobikestore.service.AuthenticationService;
import com.example.motobikestore.service.email.CustomMailSender;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.motobikestore.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;
    private final JwtProvider jwtProvider;
    private final CustomMailSender customMailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${hostname}")
    private String hostname;

    @Value("${recaptcha.secret}")
    private String secret;

    @Value("${recaptcha.url}")
    private String captchaUrl;

    @Override
    public Map<String, Object> login(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            Users users = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
            String accountRole = users.getRoles().iterator().next().name();
            String token = jwtProvider.createToken(email, accountRole);
            Map<String, Object> response = new HashMap<>();
            response.put("user", users);
            response.put("token", token);
            return response;
        } catch (AuthenticationException e) {
            throw new ApiRequestException(INCORRECT_PASSWORD, HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public String registerUser(Users users, String captcha, String password2) {
        String url = String.format(captchaUrl, secret, captcha);
        restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);

        if (users.getPassword() != null && !users.getPassword().equals(password2)) {
            throw new PasswordException(PASSWORDS_DO_NOT_MATCH);
        }

        if (userRepository.findByEmail(users.getEmail()).isPresent()) {
            throw new EmailException(EMAIL_IN_USE);
        }
        users.setActive(false);
        users.setRoles(Collections.singleton(Role.USER));
//        user.setProvider(AuthProvider.LOCAL);
        users.setActivationCode(UUID.randomUUID().toString());
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        userRepository.save(users);

        sendEmail(users, "Activation code", "registration-template", "registrationUrl", "/activate/" + users.getActivationCode());
        return "User successfully registered.";
    }

//    @Override
//    @Transactional
//    public User registerOauth2User(String provider, OAuth2UserInfo oAuth2UserInfo) {
//        User user = new User();
//        user.setEmail(oAuth2UserInfo.getEmail());
//        user.setFirstName(oAuth2UserInfo.getFirstName());
//        user.setLastName(oAuth2UserInfo.getLastName());
//        user.setActive(true);
//        user.setRoles(Collections.singleton(Role.USER));
//        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
//        return userRepository.save(user);
//    }

//    @Override
//    @Transactional
//    public User updateOauth2User(User user, String provider, OAuth2UserInfo oAuth2UserInfo) {
//        user.setFirstName(oAuth2UserInfo.getFirstName());
//        user.setLastName(oAuth2UserInfo.getLastName());
//        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
//        return userRepository.save(user);
//    }

    @Override
    public String getEmailByPasswordResetCode(String code) {
        return userRepository.getEmailByPasswordResetCode(code)
                .orElseThrow(() -> new ApiRequestException(INVALID_PASSWORD_CODE, HttpStatus.BAD_REQUEST));
    }

    @Override
    @Transactional
    public String sendPasswordResetCode(String email) {
        Users users = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
        users.setPasswordResetCode(UUID.randomUUID().toString());
        userRepository.save(users);

        sendEmail(users, "Password reset", "password-reset-template", "resetUrl", "/reset/" + users.getPasswordResetCode());
        return "Reset password code is send to your E-mail";
    }

    @Override
    @Transactional
    public String passwordReset(String email, String password, String password2) {
        if (StringUtils.isEmpty(password2)) {
            throw new PasswordConfirmationException(EMPTY_PASSWORD_CONFIRMATION);
        }
        if (password != null && !password.equals(password2)) {
            throw new PasswordException(PASSWORDS_DO_NOT_MATCH);
        }
        Users users = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
        users.setPassword(passwordEncoder.encode(password));
        users.setPasswordResetCode(null);
        userRepository.save(users);
        return "Password successfully changed!";
    }

    @Override
    @Transactional
    public String activateUser(String code) {
        Users users = userRepository.findByActivationCode(code)
                .orElseThrow(() -> new ApiRequestException(ACTIVATION_CODE_NOT_FOUND, HttpStatus.NOT_FOUND));
        users.setActivationCode(null);
        users.setActive(true);
        userRepository.save(users);
        return "User successfully activated.";
    }

    private void sendEmail(Users users, String subject, String template, String urlAttribute, String urlPath) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("firstName", users.getFirstName());
        attributes.put(urlAttribute, "http://" + hostname + urlPath);
        customMailSender.sendMessageHtml(users.getEmail(), subject, template, attributes);
    }
}
