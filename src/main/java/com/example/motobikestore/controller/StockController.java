package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.StockDTO;
import com.example.motobikestore.service.StockService;
import com.example.motobikestore.view.StockView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.motobikestore.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_ADMIN+"/stock")
public class StockController {
    @Autowired
    StockService stockService;
    @PostMapping(ADD)
    public ResponseEntity<String> CreateReceipt(@RequestBody StockDTO stockDTO){
        return ResponseEntity.ok(stockService.CreateReceipt(stockDTO));
    }
    @GetMapping(GET)
    public Iterable<StockView> getReceipt(){
        return stockService.getReceipt();
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelReceipt(@PathVariable Long id){
        return ResponseEntity.ok(stockService.cancelReceipt(id));
    }
}
