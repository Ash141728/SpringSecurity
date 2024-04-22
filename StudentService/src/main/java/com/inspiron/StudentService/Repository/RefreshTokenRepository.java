package com.inspiron.StudentService.Repository;

import com.inspiron.StudentService.Model.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);
}
