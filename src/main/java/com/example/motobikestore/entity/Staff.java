package com.example.motobikestore.entity;

import com.example.motobikestore.enums.Sex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Staff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID staffID;
    @NotNull(message="Gender cannot be null")
    private Sex sex;
    @Pattern(regexp="\\d{9}|\\d{10}", message="Phone number must be 9 or 10 digits")
    @Column(nullable = false, length = 15,unique=true)
    private String phone;
    @NotNull(message="Birth date cannot be null")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Past(message="Birth date must be in the past")
    private LocalDate birth;
    @Pattern(regexp="\\d{9}|\\d{12}", message="Identity ID must be 9 or 12 digits")
    @Column(name ="cccd",unique=true)
    private String cccd;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "staff", cascade = CascadeType.ALL)
    private Collection<Orders> orders;
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "userID")
    private Users users;
    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "staffID")
    @JsonIgnore
    private Staff manager;
}
