package com.example.motobikestore.DTO;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    UUID userID;
    @JsonUnwrapped
    List<ListProduct> listProduct = new ArrayList<>();
}
