package com.example.LojaModerna.Services;

import java.util.Optional;
import java.util.Random;

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
public User create(User obj) {
    // Verifica se já existe um usuário com o mesmo email
    Optional<User> existingUserOpt = userRepository.findByEmail(obj.getEmail());

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

        return userRepository.save(existingUser);
    } else {
        // Define badge padrão se não vier do front
        if (obj.getBadge() == null) {
            obj.setBadge(Badge.NOVATO);
        }

        obj.setBadgeCode(generateUniqueBadgeCode());
        return userRepository.save(obj); // ID será gerado automaticamente aqui
    }
}

    public void delete(String id, String badgeCode, User obj) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (badgeCode == null || badgeCode.isEmpty()) {
            throw new RuntimeException("Código do crachá não informado.");
        }

        if (!existingUser.getBadgeCode().equals(badgeCode)) {
            throw new RuntimeException("Confirmação do dono inválida. Exclusão cancelada.");
        }

        userRepository.delete(existingUser);
    }

    public boolean authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.map(user -> user.getPassword().equals(password)).orElse(false);
    }

    
    private String generateUniqueBadgeCode() {
        String code;
        do {
            code = String.format("%06d", new Random().nextInt(1000000));
        } while (userRepository.findByBadgeCode(code).isPresent());
        return code;
    }
}
