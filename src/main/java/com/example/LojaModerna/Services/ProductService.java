package com.example.LojaModerna.Services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.LojaModerna.Repositories.ProductRepository;
import com.example.LojaModerna.models.Product;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;



    public Product findById(String id){
        Optional<Product> product = this.productRepository.findById(id);
        return product.orElseThrow();
    }

    @Transactional
    public Product createProduct(Product obj){
        return productRepository.save(obj);

    }


    @Transactional
    public Product update(Product obj){
        //por enquanto o return usa o obj n está sendo usado essa func!
        return obj;
    }


    public Product  saleProduct(String id, Product obj){
        Product product = this.productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if(product.getId().equals(obj.getId())){
            try{
                this.deleteProduct(id);
                return product;
            }catch (Exception e) {
                 throw new RuntimeException("Erro ao realizar a venda: " + e.getMessage());
             }

        }else{
            throw new RuntimeException("ID do produto não corresponde.");
        }
    }

    public void deleteProduct(String id) throws Exception{
        findById(id);
        //o try catch ainda n será totalmente finalizado. Falta criar um exception personalizada!
        try {
            this.productRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Não é possivel deletar sua compra ");
        }
    }

    

}
