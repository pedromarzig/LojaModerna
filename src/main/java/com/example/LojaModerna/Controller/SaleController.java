package com.example.LojaModerna.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LojaModerna.Services.SaleService;
import com.example.LojaModerna.models.Sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/sales")
@Validated
public class SaleController {
    
     @Autowired
    private SaleService saleService;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaleRequest {
        public String productId;
        public String vendedorId;
        public int quantidade;
 
    }

    @PostMapping
    public ResponseEntity<Sale> makeSale(@RequestBody SaleRequest request) {
        Sale sale = saleService.makeSale(
            request.productId,
            request.vendedorId,
            request.quantidade
        );
        return ResponseEntity.ok(sale);
    }
}
