package com.example.motobikestore.entity;

import com.example.motobikestore.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID customerID;

    @Pattern(regexp="\\d{9}|\\d{10}", message="Phone number must be 9 or 10 digits")
    @Column(nullable = false, length = 15,unique=true)
    private String phone;

//    @NotNull(message="Gender cannot be null")
    private Sex sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    //bi-directional many-to-one association to Order
    @OneToMany(fetch = FetchType.LAZY, mappedBy="customer")
    private List<Orders> orders;

    @Valid
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID")
    private Users users;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy="address")
//    private List<Address> addressList;
}
