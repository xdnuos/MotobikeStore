package com.example.motobikestore.service;

import com.example.motobikestore.DTO.auth.AuthenticationResponse;
import com.example.motobikestore.DTO.user.CustomerRequest;

import java.time.LocalDateTime;
import java.util.Map;

public interface AuthenticationService {
    Map<String, Object> login(String email, String password);

    String registerCustomer(CustomerRequest customerRequest);

//    Customer registerOauth2User(String provider, OAuth2UserInfo oAuth2UserInfo);

//    User updateOauth2User(User user, String provider, OAuth2UserInfo oAuth2UserInfo);

    Boolean checkValidCode(LocalDateTime time1, LocalDateTime time2);
    String activateUser(String code);

    String sendPasswordResetCode(String email);

    String passwordReset(String password,String code);
    String changePassword(String email, String password);
}
