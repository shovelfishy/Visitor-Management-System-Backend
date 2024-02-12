package dev.edward.projectx.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.edward.projectx.model.RefreshToken;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String>{
    
    
}
