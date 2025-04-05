package com.example.LojaModerna.Repositories;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.LojaModerna.models.User;


@Repository
public interface UserRepository extends MongoRepository<User, String>{
    
    Optional<User> findByBadgeCode(String badgeCode);

    Optional<User> findByEmail(String email);

    
}
