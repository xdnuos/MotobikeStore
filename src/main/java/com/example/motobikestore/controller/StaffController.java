package com.example.motobikestore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.motobikestore.constants.PathConstants.API_V1_ADMIN;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_ADMIN+"/staff")
public class StaffController {
}
