package com.example.motobikestore.DTO.user;

import com.example.motobikestore.enums.Sex;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link com.example.motobikestore.entity.Customer}
 */
@Data
public class CustomerResponse extends UsersResponse {
    private String phone;
    private Sex sex;
    private LocalDate birth;
    private Integer totalOrders;
    private Integer totalProductBuy;
    private BigDecimal totalPurchased;
    private String address;
    private Float ratioOrder;
}