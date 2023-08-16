package com.example.motobikestore.service.Impl;

import com.example.motobikestore.DTO.user.CustomerRequest;
import com.example.motobikestore.entity.Address;
import com.example.motobikestore.repository.CustomerRepository;
import com.example.motobikestore.repository.AddressRepository;
import com.example.motobikestore.entity.Customer;
import com.example.motobikestore.entity.Users;
import com.example.motobikestore.enums.Role;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.exception.EmailException;
import com.example.motobikestore.exception.PasswordConfirmationException;
import com.example.motobikestore.exception.PasswordException;
import com.example.motobikestore.mapper.CustomerRequestMapper;
import com.example.motobikestore.mapper.UsersRequestMapper;
import com.example.motobikestore.repository.UsersRepository;
import com.example.motobikestore.security.JwtProvider;
import com.example.motobikestore.security.UserPrincipal;
import com.example.motobikestore.service.ActivationCodeGenerator;
import com.example.motobikestore.service.AuthenticationService;
import com.example.motobikestore.service.email.CustomMailSender;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.motobikestore.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final CustomMailSender customMailSender;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository userRepository;
    private final RestTemplate restTemplate;

    @Value("${hostname}")
    private String hostname;

    @Value("${recaptcha.secret}")
    private String secret;

    @Value("${recaptcha.url}")
    private String captchaUrl;
    private final UsersRequestMapper usersRequestMapper;
    private final CustomerRequestMapper customerRequestMapper;
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Map<String, Object> login(String email, String password) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//            Users users = userRepository.findByEmail(email)
//                    .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
            UserPrincipal userPrincipal = (UserPrincipal) authenticate.getPrincipal();
            Users users = new Users();
            users.setUserID(userPrincipal.getUserID());
            users.setEmail(userPrincipal.getEmail());
            users.setAvatar(userPrincipal.getAvatar());
            users.setFirstName(userPrincipal.getFirstName());
            users.setLastName(userPrincipal.getLastName());
            Set<Role> roles = new HashSet<>();
            Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                String role = authority.getAuthority();
                roles.add(Role.valueOf(role.toUpperCase()));
            }
            users.setRoles(roles);
            String accountRole = users.getRoles().iterator().next().name();
            String token = jwtProvider.createToken(email, accountRole);
//            System.out.println(token);
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
    public String registerCustomer(CustomerRequest customerRequest) {
//        String url = String.format(captchaUrl, secret, captcha);
//        restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);
        if (userRepository.findByEmail(customerRequest.getEmail()).isPresent()) {
            throw new EmailException(EMAIL_IN_USE);
        }
        if (customerRepository.findByPhone(customerRequest.getPhone()).isPresent()) {
            throw new EmailException(PHONE_IN_USE);
        }
//        if (customerRequest.getPassword() != null && !customerRequest.getPassword().equals(password2)) {
//            throw new PasswordException(PASSWORDS_DO_NOT_MATCH);
//        }

        Users users = usersRequestMapper.toEntity(customerRequest);
        Customer customer = customerRequestMapper.toEntity(customerRequest);


        users.setActive(false);
        users.setRoles(Collections.singleton(Role.CUSTOMER));
//        user.setProvider(AuthProvider.LOCAL);
        users.setActivationCode(ActivationCodeGenerator.generateRandomString());
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setCreateDate(LocalDateTime.now());
        customer.setUsers(users);
        customerRepository.save(customer);

        Address address = new Address();
        address.setAddress(customerRequest.getAddress());
        address.setFullname(users.getFullName());
        address.setPhone(customerRequest.getPhone());
        address.setCustomer(customer);
        addressRepository.save(address);
        System.out.println(users.getActivationCode());
        customMailSender.sendEmail(users, "Activation code", "registration-template", "registrationUrl", "/activate/" + users.getActivationCode());
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
    public Boolean checkValidCode(LocalDateTime time1,LocalDateTime time2) {
        Duration duration = Duration.ofMinutes(10);
        Duration difference = Duration.between(time1, time2);
        return difference.compareTo(duration) < 10;
    }

    @Override
    @Transactional
    public String sendPasswordResetCode(String email) {
        Users users = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
        if (!users.isActive() & users.getActivationCode()==null){
            throw new ApiRequestException("Your account is locked", HttpStatus.FORBIDDEN);
        }
        users.setPasswordResetCode(ActivationCodeGenerator.generateRandomString());
        users.setResetPassCodeCreate(LocalDateTime.now());
        userRepository.save(users);
        customMailSender.sendEmail(users, "Password reset", "password-reset-template", "resetUrl", "/reset/" + users.getPasswordResetCode());
        return "Reset password code is send to your E-mail";
    }

    @Override
    @Transactional
    public String passwordReset(String password,String code) {
//        if (StringUtils.isEmpty(password2)) {
//            throw new PasswordConfirmationException(EMPTY_PASSWORD_CONFIRMATION);
//        }
        if (StringUtils.isEmpty(code)) {
            throw new PasswordConfirmationException(EMPTY_PASSWORD_CODE);
        }
//        if (password != null && !password.equals(password2)) {
//            throw new PasswordException(PASSWORDS_DO_NOT_MATCH);
//        }
        Users users = userRepository.findByPasswordResetCode(code)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        try {
            if(users.getPasswordResetCode().equals(code)){
                if(checkValidCode(users.getResetPassCodeCreate(),LocalDateTime.now())){
                    users.setPassword(passwordEncoder.encode(password));
                    users.setPasswordResetCode(null);
//                    users.setResetPassCodeCreate(null); bo di de check lan cuoi thay doi mat khaua
                    userRepository.save(users);
                    return "Password successfully changed!";
                }
            }
        } catch (ArithmeticException e){

        }
        return INVALID_PASSWORD_CODE;

    }
    @Override
    @Transactional
    public String changePassword(String email, String password) {
//        if (StringUtils.isEmpty(password2)) {
//            throw new PasswordConfirmationException(EMPTY_PASSWORD_CONFIRMATION);
//        }
//        if (password != null && !password.equals(password2)) {
//            throw new PasswordException(PASSWORDS_DO_NOT_MATCH);
//        }
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
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        try {
            if (users.getActivationCode().equals(code)){
                users.setActivationCode(null);
                users.setActive(true);
                userRepository.save(users);
                return "User successfully activated.";
            }
        }catch (AssertionError e){

        }
        return "Error when activated user";
    }
}
