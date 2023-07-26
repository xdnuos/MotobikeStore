package com.example.motobikestore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name = "stock")
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long stockID;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createDate;

    @ManyToMany(mappedBy = "stocks",fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<Product> productstock = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "staffID")
    private Staff staff;
}
