package com.example.LojaModerna.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.LojaModerna.Repositories.UserRepository;
import com.example.LojaModerna.models.User;
import com.example.LojaModerna.models.enums.Badge;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User create(String id, User obj) {
        Optional<User> existingUserOpt = userRepository.findById(id);
    
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
    
            if (!existingUser.getName().equals(obj.getName())) {
                existingUser.setName(obj.getName());
            }

            if (!existingUser.getPassword().equals(obj.getPassword())) {
                existingUser.setPassword(obj.getPassword());
            }

            if (obj.getBadge() != null && existingUser.getBadge() != obj.getBadge()) {
                existingUser.setBadge(obj.getBadge());
            }

            if (!existingUser.getEmail().equals(obj.getEmail())) {
                existingUser.setEmail(obj.getEmail());
            }

            return userRepository.save(existingUser);
        } else {
            return userRepository.save(obj);
        }
    }

    public void delete(String id, String badgeCodeStr, User obj) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (obj.getBadge() == null) {
            throw new RuntimeException("Código do crachá não informado.");
        }

        
        Badge badgeCode;
        try {
            badgeCode = Badge.valueOf(badgeCodeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Código do crachá inválido. Exclusão não permitida.");
        }

        if (existingUser.getBadge() == obj.getBadge()) {
            if (badgeCode == existingUser.getBadge()) {
                userRepository.delete(existingUser);
            } else {
                throw new RuntimeException("Confirmação do dono inválida. Exclusão cancelada.");
            }
        } else {
            throw new RuntimeException("Código do crachá inválido. Exclusão não permitida.");
        }
    }

    public boolean authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
    
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getPassword().equals(password); 
        }
        return false;
    }
}
