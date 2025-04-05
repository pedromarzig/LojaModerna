package com.example.LojaModerna.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.LojaModerna.models.Sale;

@Repository
public interface SaleRepository extends MongoRepository<Sale, String>  {
    
}
