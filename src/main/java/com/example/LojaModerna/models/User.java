package com.example.LojaModerna.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.LojaModerna.models.enums.Badge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    @Getter
    private String id;

    @Getter
    private String name;

    @Getter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    private Badge badge;  // ⬅️ era String, agora é o enum
}
