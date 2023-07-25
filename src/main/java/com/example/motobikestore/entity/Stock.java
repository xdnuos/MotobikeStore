package com.example.motobikestore.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long stockID;

    @Column
    @DateTimeFormat
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "staffID")
    private Staff staff;
}
