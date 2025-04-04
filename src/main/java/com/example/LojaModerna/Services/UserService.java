package com.example.LojaModerna.Services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.LojaModerna.Repositories.UserRepository;
import com.example.LojaModerna.models.User;

@Service
public class UserService {
    

    @Autowired
    private UserRepository userRepository;


    public User findById(String id){
        Optional<User> optionalUser = userRepository.findById(id);
        
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }else{
            return null;
        }

    }

    public User create(String id, User obj) {
        //o try catch ainda n será totalmente finalizado. Falta criar um exception personalizada!
        try {
            User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    
             // Atualiza apenas os campos que mudaram (opcional)
        if (!existingUser.getName().equals(obj.getName())) {
            existingUser.setName(obj.getName());
        }

        if (!existingUser.getPassword().equals(obj.getPassword())) {
            existingUser.setPassword(obj.getPassword());
        }

        if (!existingUser.getBadgeCode().equals(obj.getBadgeCode())) {
            existingUser.setBadgeCode(obj.getBadgeCode());
        }

        if (!existingUser.getEmail().equals(obj.getEmail())) {
            existingUser.setEmail(obj.getEmail());
        }

            return userRepository.save(existingUser);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return null; 
        }
    }


    public User update(String id, String badgeCode, User obj) {
        try {
            User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    
            if (!existingUser.getBadgeCode().equals(badgeCode)) {
                throw new RuntimeException("Código do crachá inválido.");
            }
    
            existingUser.setPassword(obj.getPassword());
    
            return userRepository.save(existingUser);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return null; 
        }
    }

    public void delete(String id, String badgeCode, User obj){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        if (obj.getBadgeCode() == null || obj.getBadgeCode().isEmpty()) {   
          throw new RuntimeException("Código do crachá não informado.");
        }       

        if(existingUser.getBadgeCode().equals(obj.getBadgeCode())){

            if(badgeCode.equals(existingUser.getBadgeCode())){
                userRepository.delete(existingUser);
            }else{
                throw new RuntimeException("Confirmação do dono inválida. Exclusão cancelada.");
            }

        }else{
            throw new RuntimeException("Código do crachá inválido. Exclusão não permitida.");
        }


        
    }


    




}
