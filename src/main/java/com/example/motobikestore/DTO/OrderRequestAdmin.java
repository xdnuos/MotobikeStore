package com.example.motobikestore.DTO;

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
public class OrderRequestAdmin {
    @NotNull
    List<Long> cartProductIDs;
    @NotNull
    UUID UserID;
    UUID customerID;
    String firstName;
    String lastName;
    @NotNull
    String phone;
}
