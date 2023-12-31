package com.example.motobikestore.DTO;

import com.example.motobikestore.enums.Payment;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRequestCustomer {
    @NotNull
    List<Long> cartProductIDs;
    UUID userID;
    Long addressID;
    Payment payment;
    String note;
}
