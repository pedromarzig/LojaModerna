package com.example.LojaModerna.models;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.LojaModerna.models.enums.SaleStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    private String id;

    @DBRef
    private Product product;

    @DBRef
    private User vendedor;
    
    private int quantidade;
    private double total;
    private double comissao;
    private SaleStatus saleStatus;
    private LocalDateTime data;
}
