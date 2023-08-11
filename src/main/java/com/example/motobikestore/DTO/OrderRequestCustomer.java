package com.example.motobikestore.DTO;

import com.example.motobikestore.enums.Payment;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestCustomer {
    @NotNull
    List<Long> cartProductIDs;
    UUID customerID;
    Long addressID;
    Payment payment;
}
