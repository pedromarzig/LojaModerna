package com.example.LojaModerna.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LojaModerna.Services.UserService;
import com.example.LojaModerna.Services.security.JwtUtil;
import com.example.LojaModerna.models.User;
import com.example.LojaModerna.models.dto.LoginDTO;
import com.example.LojaModerna.models.dto.LoginResponseDTO;

@RestController
@RequestMapping("/users")
@Validated
public class LoginController {
    

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id){
        User obj = this.userService.findById(id);
        return ResponseEntity.ok().body(obj);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            System.out.println("Recebido login para: " + loginDTO);

            if (loginDTO == null) {
                System.out.println("loginDTO está nulo");
            } else {
                System.out.println("Email: " + loginDTO.getEmail());
                System.out.println("Senha: " + loginDTO.getPassword());
            }

            boolean isAuthenticated = userService.authenticate(loginDTO.getEmail(), loginDTO.getPassword());
            
            if (isAuthenticated) {
                String token = jwtUtil.generateToken(loginDTO.getEmail());
                return ResponseEntity.ok().body(new LoginResponseDTO(token));
            } else {
                return ResponseEntity.status(401).body(Map.of("error", "Credenciais inválidas"));
            }
        } catch (Exception e) {
            System.err.println("Erro durante o processo de login: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erro interno no servidor"));
        }
    }




    @GetMapping("/encrypt-senhas")
    public ResponseEntity<String> encryptSenhasAntigas() {
        userService.encryptOldPasswords();
        return ResponseEntity.ok("Senhas atualizadas com sucesso");
    }

}
