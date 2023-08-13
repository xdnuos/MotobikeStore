package com.example.motobikestore.service;

import com.example.motobikestore.DTO.StockDTO;
import com.example.motobikestore.entity.Product;
import com.example.motobikestore.entity.Staff;
import com.example.motobikestore.entity.Stock;
import com.example.motobikestore.entity.StockItem;
import com.example.motobikestore.enums.Status;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.repository.ProductRepository;
import com.example.motobikestore.repository.StaffRepository;
import com.example.motobikestore.repository.StockRepository;
import com.example.motobikestore.repository.blaze.StockViewRepository;
import com.example.motobikestore.view.StockView;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.motobikestore.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class StockService {
    @Autowired
    StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private StockViewRepository stockViewRepository;

    @Transactional
    public String CreateReceipt(StockDTO stockDTO){
        Staff staff = staffRepository.findByUsers_UserID(stockDTO.getUserID())
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        Stock stock = new Stock();
        List<StockItem> stockItems = new ArrayList<>();
        stockDTO.getListProduct().forEach(item -> {
            StockItem stockItem = new StockItem();
            Product product = productRepository.findById(item.getProductID())
                    .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
            stockItem.setProduct(product);
            stockItem.setQuantity(item.getQuantity());
            stockItem.setStock(stock);
            stockItems.add(stockItem);
            product.setStock(product.getStock()+item.getQuantity());
            productRepository.save(product);
        });
        stock.setStaff(staff);
        stock.setStockItems(stockItems);
        stock.setCreateDate(LocalDateTime.now());
        stock.setStatus(Status.SUCCESS);
        stockRepository.save(stock);
        return "Success";
    }
    public Iterable<StockView> getReceipt(){
        return stockViewRepository.findAll();
    }
    @Transactional
    public String cancelReceipt(Long id){
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Receipt not found", HttpStatus.NOT_FOUND));
        stock.setStatus(Status.CANCEL);
        List<StockItem> stockItems = stock.getStockItems();
        stockItems.forEach(stockItem -> {
            Product product = stockItem.getProduct();
            Integer quantity = stockItem.getQuantity();
            product.setStock(product.getStock()-quantity);
            productRepository.save(product);
        });
        stockRepository.save(stock);
        return "Success";
    }
}
