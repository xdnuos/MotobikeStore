package com.example.motobikestore.service;

import com.example.motobikestore.DTO.StaffChangeState;
import com.example.motobikestore.DTO.user.StaffRequest;
import com.example.motobikestore.DTO.user.StaffResponse;
import com.example.motobikestore.entity.Staff;
import com.example.motobikestore.entity.Users;
import com.example.motobikestore.enums.Role;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.mapper.StaffRequestMapper;
import com.example.motobikestore.mapper.StaffResponseMapper;
import com.example.motobikestore.repository.StaffRepository;
import com.example.motobikestore.repository.UsersRepository;
import com.example.motobikestore.service.email.CustomMailSender;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.motobikestore.constants.ErrorMessage.*;
import static com.example.motobikestore.constants.SuccessMessage.*;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomMailSender customMailSender;
    private final StaffRequestMapper staffRequestMapper;
    private final UsersRepository usersRepository;
    private final StaffResponseMapper staffResponseMapper;

    @Transactional
    public String addStaff(StaffRequest staffRequest){
        Staff mangager = staffRepository.findByUsers_UserID(staffRequest.getManagerID())
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        if(usersRepository.existsByEmail(staffRequest.getEmail())){
            throw  new ApiRequestException(EMAIL_IN_USE, HttpStatus.NOT_FOUND);
        }
        Set<Role> staffRoles = new HashSet<>();
        staffRoles.add(staffRequest.getRole());
        isManager(mangager.getUsers().getRoles(),staffRoles);

        Users users = new Users();
        users.setEmail(staffRequest.getEmail());
        users.setFirstName(staffRequest.getFirstName());
        users.setLastName(staffRequest.getLastName());
        users.setRoles(Collections.singleton(staffRequest.getRole()));
        users.setActive(false);
        users.setActivationCode(ActivationCodeGenerator.generateRandomString());
        users.setCreateDate(LocalDateTime.now());
        users.setPassword(passwordEncoder.encode("123456"));

        Staff staff = new Staff();
        staff.setBirth(staffRequest.getBirth());
        staff.setCccd(staffRequest.getCccd());
        staff.setSex(staffRequest.getSex());
        staff.setPhone(staffRequest.getPhone());
        staff.setUsers(users);
        staff.setManager(mangager);
        staffRepository.save(staff);
        customMailSender.sendEmail(users, "Activation code", "registration-template", "registrationUrl", "/activate/" + users.getActivationCode());
        return SUCCESS_ADD_USER;
    }

    private void isManager(Set<Role> manager,Set<Role> staff){
        if ((staff.contains(Role.MASTER) | staff.contains(Role.ADMIN))& !manager.contains(Role.MASTER)){
            throw new ApiRequestException("You do not have this authority", HttpStatus.FORBIDDEN);
        }
    }
    public List<StaffResponse> getListStaff(){
        return staffRepository.findAll().stream().map(staffResponseMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public String changeState(StaffChangeState staffChangeState){
        if(staffChangeState.getUserIDStaff().equals(staffChangeState.getUserIDManager())){
            throw new ApiRequestException("You can't disable the account yourself", HttpStatus.FORBIDDEN);
        }
        Users manager = usersRepository.findById(staffChangeState.getUserIDManager())
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        Users staff = usersRepository.findById(staffChangeState.getUserIDStaff())
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        isManager(manager.getRoles(),staff.getRoles());

        boolean isActive = staff.isActive();
        usersRepository.changeStatusByID(staff.getUserID(),!isActive);
        return isActive ? SUCCESS_DISABLE_USER : SUCCESS_ENABLE_USER;
    }

    @Transactional
    public String resetPassword(UUID uuid){
        Users users = usersRepository.findById(uuid)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        users.setPassword(passwordEncoder.encode("123456"));
        usersRepository.save(users);

        return "Success. Default password is 123456";
    }
}
