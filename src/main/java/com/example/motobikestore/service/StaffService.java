//package com.example.motobikestore.service;
//
//import com.example.motobikestore.DTO.user.StaffRequest;
//import com.example.motobikestore.entity.Staff;
//import com.example.motobikestore.entity.Users;
//import com.example.motobikestore.enums.Role;
//import com.example.motobikestore.mapper.StaffRequestMapper;
////import com.example.motobikestore.mapper.UsersRequestMapper;
//import com.example.motobikestore.repository.StaffRepository;
//import com.example.motobikestore.repository.UserRepository;
//import com.example.motobikestore.service.email.CustomMailSender;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//
//import static com.example.motobikestore.constants.ErrorMessage.EMAIL_IN_USE;
//import static com.example.motobikestore.constants.SuccessMessage.SUCCESS_ADD_USER;
//
//@Service
//@RequiredArgsConstructor
//public class StaffService {
//    private final StaffRepository staffRepository;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    private final CustomMailSender customMailSender;
//    private final StaffRequestMapper staffRequestMapper;
//    private final UsersRequestMapper usersRequestMapper;
//
//    public String addStaff(StaffRequest staffRequest, Role role){
//        if(!userRepository.existsByEmail(staffRequest.getEmail())){
//            String activationCode = ActivationCodeGenerator.generateActivationCode();
//            Staff staff = staffRequestMapper.toEntity(staffRequest);
//            Users users = usersRequestMapper.toEntity(staffRequest);
//            users.setRoles(Collections.singleton(role));
//            users.setActive(false);
//            users.setCreateDate(LocalDateTime.now());
//            users.setActivationCode(activationCode);
//            staff.setUsers(users);
////            staff.setManager();
//            users.setPassword(passwordEncoder.encode(users.getPassword()));
//            staffRepository.save(staff);
//
//            customMailSender.sendEmail(users, "Activation code", "registration-template", "registrationUrl", "/activate/" + users.getActivationCode());
//            return SUCCESS_ADD_USER;
//        }
//        return EMAIL_IN_USE;
//    }
//}
