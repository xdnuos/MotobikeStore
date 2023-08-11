package com.example.motobikestore.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ConfirmOrderDTO {
    public UUID userID;
    public Long orderID;
}
