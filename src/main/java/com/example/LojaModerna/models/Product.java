package com.example.LojaModerna.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {
    

    @Id
    private String id;
    private String nameProduct;
    private String description;
    private Double quantity;
    private Double price;


    public Product(String id, String nameProduct, String description, Double price, Double quantity) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(){}

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity(){
        return quantity;
    }

    public void setQuantity(Double quantity){
        this.quantity = quantity;
    }


    
}
