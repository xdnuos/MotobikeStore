package com.example.motobikestore.service;

import com.example.motobikestore.DTO.StaffChangeState;
import com.example.motobikestore.DTO.user.StaffRequest;
import com.example.motobikestore.DTO.user.StaffResponse;
import com.example.motobikestore.entity.Images;
import com.example.motobikestore.entity.Staff;
import com.example.motobikestore.entity.Users;
import com.example.motobikestore.enums.Role;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.mapper.StaffRequestMapper;
import com.example.motobikestore.mapper.StaffResponseMapper;
import com.example.motobikestore.mapper.UsersRequestMapper;
import com.example.motobikestore.repository.StaffRepository;
import com.example.motobikestore.repository.UsersRepository;
import com.example.motobikestore.service.email.CustomMailSender;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UsersRequestMapper usersRequestMapper;
    @Autowired
    private CloudinaryService cloudinaryService;
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

        Users users = usersRequestMapper.toEntity(staffRequest);
//        users.setEmail(staffRequest.getEmail());
//        users.setFirstName(staffRequest.getFirstName());
//        users.setLastName(staffRequest.getLastName());
        users.setRoles(Collections.singleton(staffRequest.getRole()));
        users.setActive(false);
        users.setActivationCode(ActivationCodeGenerator.generateRandomString());
        users.setCreateDate(LocalDateTime.now());
        users.setPassword(passwordEncoder.encode("123456"));

        Staff staff = staffRequestMapper.toEntity(staffRequest);
//        staff.setBirth(staffRequest.getBirth());
//        staff.setCccd(staffRequest.getCccd());
//        staff.setSex(staffRequest.getSex());
//        staff.setPhone(staffRequest.getPhone());
        staff.setUsers(users);
        staff.setManager(mangager);
        staffRepository.save(staff);
        customMailSender.sendEmail(users, "Activation code", "registration-template", "registrationUrl", "/activate/" + users.getActivationCode());
        return SUCCESS_ADD_USER;
    }

    @Transactional
    public String editStaff(StaffRequest staffRequest){
        Staff staff = staffRepository.findById(staffRequest.getStaffID())
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        if(!staff.getPhone().equals(staffRequest.getPhone())){
            if(staffRepository.existsByPhone(staffRequest.getPhone())){
                throw  new ApiRequestException(PHONE_IN_USE, HttpStatus.NOT_ACCEPTABLE);
            }
        }
        if(!staff.getCccd().equals(staffRequest.getCccd())){
            if(staffRepository.existsByCccd(staffRequest.getCccd())){
                throw  new ApiRequestException(CCCD_IN_USE, HttpStatus.NOT_ACCEPTABLE);
            }
        }

        if (staff.getUsers().getAvatar()!=null & staffRequest.getImg()!=null){
            String originalFileName = cloudinaryService.extractFileNameFromUrl(staff.getUsers().getAvatar().getImagePath());
            if (!originalFileName.equals(staffRequest.getImg().getOriginalFilename())){
                String imageUrl = cloudinaryService.uploadImage(staffRequest.getImg());
                staff.getUsers().getAvatar().setImagePath(imageUrl);
            }
        }
        else if (staffRequest.getImg()!=null){
            Images images = new Images();
            String imageUrl = cloudinaryService.uploadImage(staffRequest.getImg());
            images.setImagePath(imageUrl);
            staff.getUsers().setAvatar(images);
        }
        else {
            staff.getUsers().setAvatar(null);
        }
        staff.setBirth(staffRequest.getBirth());
        staff.setCccd(staffRequest.getCccd());
        staff.setPhone(staffRequest.getPhone());
        staff.setSex(staffRequest.getSex());
        Users users = staff.getUsers();

        if(!users.getEmail().equals(staffRequest.getEmail())){
            if(usersRepository.existsByEmail(staffRequest.getEmail())){
                throw  new ApiRequestException(EMAIL_IN_USE, HttpStatus.NOT_ACCEPTABLE);
            }
        }
        users.setEmail(staffRequest.getEmail());
        users.setFirstName(staffRequest.getFirstName());
        users.setLastName(staffRequest.getLastName());
        users.setRoles(Collections.singleton(staffRequest.getRole()));
        staff.setUsers(users);
        staffRepository.save(staff);
        return SUCCESS_UPDATE_USER;
    }

    private void isManager(Set<Role> manager,Set<Role> staff){
        if ((staff.contains(Role.MASTER) | staff.contains(Role.ADMIN))& !manager.contains(Role.MASTER)){
            throw new ApiRequestException("You do not have this authority", HttpStatus.FORBIDDEN);
        }
    }
    public List<StaffResponse> getListStaff(){
        return staffRepository.findAll().stream().map(staffResponseMapper::toDto).collect(Collectors.toList());
    }
    public StaffResponse getStaff(UUID staffID){
        Staff staff = staffRepository.findById(staffID)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        return staffResponseMapper.toDto(staff);
    }

    public StaffResponse getStaffByUser(UUID userID){
        Staff staff = staffRepository.findByUsers_UserID(userID)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        return staffResponseMapper.toDto(staff);
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
