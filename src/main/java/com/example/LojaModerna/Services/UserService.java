package com.example.LojaModerna.Services;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.LojaModerna.Repositories.UserRepository;
import com.example.LojaModerna.models.User;
import com.example.LojaModerna.models.enums.Badge;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
                // Se for diferente, criptografa a nova senha antes de salvar
                existingUser.setPassword(passwordEncoder.encode(obj.getPassword()));
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

            // Criptografa a senha antes de salvar o novo usuário
            obj.setPassword(passwordEncoder.encode(obj.getPassword()));

            obj.setBadgeCode(generateUniqueBadgeCode());
            return userRepository.save(obj); // ID será gerado automaticamente aqui
        }
    }

    @Transactional
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

    // Método para criptografar senhas antigas
    public void encryptOldPasswords() {
        for (User user : userRepository.findAll()) {
            String password = user.getPassword();
            // Verifica se a senha ainda não está criptografada (começa com $2a é típico do BCrypt)
            if (!password.startsWith("$2a$")) {
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
            }
        }
    }

    public boolean authenticate(String email, String password) {
        try {
            System.out.println("Iniciando autenticação para: " + email);
            Optional<User> userOptional = userRepository.findByEmail(email);
            
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                System.out.println("Usuário encontrado: " + user.getEmail());
                System.out.println("Senha vinda do banco (criptografada): " + user.getPassword());
                boolean match = passwordEncoder.matches(password, user.getPassword());
                System.out.println("Senha corresponde? " + match);
                return match;
            } else {
                System.out.println("Usuário não encontrado");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Erro durante a autenticação: " + e.getMessage());
            e.printStackTrace();
            return false; 
        }
    }
    
    
    

    private String generateUniqueBadgeCode() {
        String code;
        do {
            code = String.format("%06d", new Random().nextInt(1000000));
        } while (userRepository.findByBadgeCode(code).isPresent());
        return code;
    }
}
