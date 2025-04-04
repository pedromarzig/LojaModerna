package com.example.LojaModerna.Services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.LojaModerna.Repositories.ProductRepository;
import com.example.LojaModerna.Repositories.SaleRepository;
import com.example.LojaModerna.Repositories.UserRepository;
import com.example.LojaModerna.models.Product;
import com.example.LojaModerna.models.Sale;
import com.example.LojaModerna.models.User;

@Service
public class SaleService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired 
    private UserRepository userRepository;

    public Sale makeSale(String productId, String vedendorId, int quantidade){

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        User vendedor = userRepository.findById(vedendorId)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));

        if(product.getQuantity() < quantidade){
            throw new RuntimeException("Estoque insuficiente");
        }

        double total = product.getPrice() * quantidade;
        double comissao = total * 0.05;

        product.setQuantity(product.getQuantity() - quantidade);
        productRepository.save(product);

        Sale venda = new Sale();
        venda.setVendedor(vendedor);
        venda.setProduct(product);
        venda.setQuantidade(quantidade);
        venda.setTotal(total);
        venda.setComissao(comissao);
        venda.setData(LocalDateTime.now());

        return saleRepository.save(venda);

    }
}
