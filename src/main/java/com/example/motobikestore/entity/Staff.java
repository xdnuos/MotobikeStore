package com.example.motobikestore.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table
@Data
public class Staff implements Serializable {
    @Id
    private UUID staffID;

    @NotNull(message="Gender cannot be null")
    private boolean sex;

    @Pattern(regexp="\\d{9}|\\d{10}", message="Phone number must be 9 or 10 digits")
    @Column(nullable = false, length = 15,unique=true)
    private String phone;

    @NotNull(message="Birth date cannot be null")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message="Birth date must be in the past")
    private Date birth;

    @Pattern(regexp="\\d{9}|\\d{12}", message="Identity ID must be 9 or 12 digits")
    @Column(name ="cccd",unique=true)
    private String cccd;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "staff", cascade = CascadeType.ALL)
    private Collection<Orders> orders;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "userID")
    private User user;

    //	@Column(name = "IDManager")
    @ManyToOne
    private Staff manager;
}
