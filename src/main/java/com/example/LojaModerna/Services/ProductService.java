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


    @Autowired
    private SaleService saleService;

    public Product findById(String id){
        Optional<Product> product = this.productRepository.findById(id);
        return product.orElseThrow();
    }

    @Transactional
    public void createProduct(Product product) {
        productRepository.save(product); // <-- isso tem que estar aqui!
    }


    @Transactional
    public Product update(Product obj){
        //por enquanto o return usa o obj n está sendo usado essa func!
        return obj;
    }


    public Product  saleProduct(String id, Product obj, String vendedorId, int quantidade){
        Product product = this.productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (!product.getId().equals(obj.getId())) {
            throw new RuntimeException("ID do produto não corresponde.");
        }

        try {
            
            saleService.makeSale(id, vendedorId, quantidade);
    
           
            this.deleteProduct(id);
    
            return product;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar a venda: " + e.getMessage());
        }
    }

    public void deleteProduct(String id) {
        try {
            Product product = findById(id); 
            productRepository.delete(product); 
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível deletar o produto.");
        }
    }
    

    

}
