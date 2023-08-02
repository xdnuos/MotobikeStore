package com.example.motobikestore.entity;

import com.example.motobikestore.enums.Arrival;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name="product")
public class Product implements Serializable {
    @Id
    @Column(name = "productID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productID;

    @Size(min = 2, max = 8,message = "Độ dài SKU từ 2-8 kí tự")
    @Column(name = "sku", length = 8)
    @NotEmpty(message = "Vui lòng nhập mã SKU")
    private String sku;

    @Size(min = 4, max = 128,message = "Độ dài tên sản phẩm từ 4-128 kí tự")
    @Column(name = "name", length = 128)
    @NotEmpty(message = "Vui lòng nhập tên sản phẩm")
    private String name;

    @Column(name = "price")
    @Min(value=1, message="Giá sản phẩm không hợp lệ")
    private BigDecimal price;

    @Size(min = 20, max = 512, message="Độ dài mô tả từ 20-256 kí tự ")
    @Column(name = "shortDescription", length = 512)
    private String shortDescription;

    @Size(min = 20, max = 4086, message="Độ dài mô tả từ 20-4086 kí tự ")
    @Column(name = "fullDescription", length = 4086)
    private String fullDescription;

    @Enumerated(EnumType.STRING)
    private Arrival arrival;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "saleCount")
    private Integer saleCount;

    @Column(name="active")
    private Boolean active;

    @Column(name="stock")
    private Integer stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinColumn(name = "manufacturerID")
    private Manufacturer manufacturer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "productID"),
            inverseJoinColumns = @JoinColumn(name = "categoryID"))
    private Set<Category> categoryList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_tag",
            joinColumns = @JoinColumn(name = "productID"),
            inverseJoinColumns = @JoinColumn(name = "tagID"))
    @JsonManagedReference
    private Set<Tag> tagList ;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinTable(name = "product_stock",
            joinColumns = @JoinColumn(name = "productID"),
            inverseJoinColumns = @JoinColumn(name = "stockID"))
    private List<Stock> productStock;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> oderItem;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Images> imagesList;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
//    private List<Variation> variations = new ArrayList<>();
}
