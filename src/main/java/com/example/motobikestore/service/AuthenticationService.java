package com.example.motobikestore.service;

import com.example.motobikestore.entity.Users;

import java.util.Map;

public interface AuthenticationService {
    Map<String, Object> login(String email, String password);

    String registerUser(Users users, String captcha, String password2);

//    Customer registerOauth2User(String provider, OAuth2UserInfo oAuth2UserInfo);

//    User updateOauth2User(User user, String provider, OAuth2UserInfo oAuth2UserInfo);

    String activateUser(String code);

    String getEmailByPasswordResetCode(String code);

    String sendPasswordResetCode(String email);

    String passwordReset(String email, String password, String password2);
}
