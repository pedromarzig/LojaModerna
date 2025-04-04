package com.example.LojaModerna.models;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.LojaModerna.models.enums.SaleStatus;


@Document(collection = "sales")
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


    public Sale(){}

    public Sale(String id, Product product, User vendedor, int quantidade, double total, double comissao,
         SaleStatus saleStatus, LocalDateTime data) {
        this.id = id;
        this.product = product;
        this.vendedor = vendedor;
        this.quantidade = quantidade;
        this.total = total;
        this.comissao = comissao;
        this.saleStatus = saleStatus;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public User getVendedor() {
        return vendedor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getTotal() {
        return total;
    }

    public double getComissao() {
        return comissao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public SaleStatus getSaleStatus(){
        return saleStatus;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setVendedor(User vendedor) {
        this.vendedor = vendedor;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setComissao(double comissao) {
        this.comissao = comissao;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setSaleStatus(SaleStatus saleStatus){
        this.saleStatus = saleStatus;
    }

    
}
