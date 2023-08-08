package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.PasswordResetRequest;
import com.example.motobikestore.DTO.auth.AuthenticationRequest;
import com.example.motobikestore.DTO.auth.AuthenticationResponse;
import com.example.motobikestore.entity.Users;
import com.example.motobikestore.exception.InputFieldException;
import com.example.motobikestore.mapper.UsersResponseMapper;
import com.example.motobikestore.security.UserPrincipal;
import com.example.motobikestore.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.motobikestore.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_AUTH)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    UsersResponseMapper usersResponseMapper;

    @PostMapping(LOGIN)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        Map<String, Object> credentials = authenticationService.login(request.getEmail(), request.getPassword());
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUser(usersResponseMapper.toDto(((Users) credentials.get("user"))));
        response.setToken((String) credentials.get("token"));
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.getToken())
                .body(response);
    }

    @GetMapping(FORGOT_EMAIL)
    public ResponseEntity<String> forgotPassword(@PathVariable String email) {
        return ResponseEntity.ok(authenticationService.sendPasswordResetCode(email));
    }

    @PostMapping(RESET)
    public ResponseEntity<String> passwordReset(@RequestBody PasswordResetRequest passwordReset) {
        return ResponseEntity.ok(authenticationService.passwordReset(passwordReset.getEmail(), passwordReset.getPassword(), passwordReset.getPassword2(),passwordReset.getCode()));
    }

    @PutMapping(EDIT_PASSWORD)
    public ResponseEntity<String> updateUserPassword(@AuthenticationPrincipal UserPrincipal user,
                                                     @Valid @RequestBody PasswordResetRequest passwordReset,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        } else {
            return ResponseEntity.ok(authenticationService.changePassword(passwordReset.getEmail(), passwordReset.getPassword(), passwordReset.getPassword2()));
        }
    }
}
