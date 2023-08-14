package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.StaffChangeState;
import com.example.motobikestore.DTO.user.StaffRequest;
import com.example.motobikestore.DTO.user.StaffResponse;
import com.example.motobikestore.entity.Staff;
import com.example.motobikestore.exception.InputFieldException;
import com.example.motobikestore.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static com.example.motobikestore.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_ADMIN+"/staff")
public class StaffController {
    @Autowired
    StaffService staffService;

    @GetMapping(GET)
    public List<StaffResponse> getStaffList(){
        return staffService.getListStaff();
    }
    @PostMapping(ADD)
    public ResponseEntity<String> addStaff(@ModelAttribute StaffRequest staffRequest,
                                           BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        return ResponseEntity.ok(staffService.addStaff(staffRequest));
    }

    @PutMapping("/changeState") ResponseEntity<String> changeState(@RequestBody StaffChangeState staffChangeState){
        return ResponseEntity.ok(staffService.changeState(staffChangeState));
    }
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MASTER')")
    @PutMapping("/resetPassword/{userID}") ResponseEntity<String> resetPassword(@PathVariable(name = "userID") UUID userID){
        return ResponseEntity.ok(staffService.resetPassword(userID));
    }
}
