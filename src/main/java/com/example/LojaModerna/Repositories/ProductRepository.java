package com.example.LojaModerna.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import com.example.LojaModerna.models.Product;


//@Repository
public interface ProductRepository  extends MongoRepository<Product, String>{
    
    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<Product> findByProductIngnoreCase(String name);

    @Query(value = "{ 'discount': { $gt: 0 } }", sort = "{ 'discount': -1 }")
    List<Product> findByProductDiscountFirst(double minDiscount);

}
