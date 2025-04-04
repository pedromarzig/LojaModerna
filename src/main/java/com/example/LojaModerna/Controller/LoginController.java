package com.example.LojaModerna.Controller;

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
import com.example.LojaModerna.models.User;
import com.example.LojaModerna.models.dto.LoginDTO;

@RestController
@RequestMapping("/users")
@Validated
public class LoginController {
    

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id){
        User obj = this.userService.findById(id);
        return ResponseEntity.ok().body(obj);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        boolean isAuthenticated = userService.authenticate(loginDTO.getEmail(), loginDTO.getPassword());
        
        if (isAuthenticated) {
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }
}
