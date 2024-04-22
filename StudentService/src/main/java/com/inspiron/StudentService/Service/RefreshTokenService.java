package com.inspiron.StudentService.Service;

import com.inspiron.StudentService.Exception.UsernameLoginExpired;
import com.inspiron.StudentService.Model.RefreshToken;
import com.inspiron.StudentService.Model.Student;
import com.inspiron.StudentService.Repository.RefreshTokenRepository;
import com.inspiron.StudentService.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    StudentRepository studentRepository;

    public RefreshToken createRefreshToken(String userName){
        RefreshToken refreshToken=new RefreshToken();
        Optional<Student> byUserName = studentRepository.findByUserName(userName);
        if(byUserName.isPresent()){
            refreshToken.setStudent(byUserName.get());
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusMillis(600000));
            return refreshTokenRepository.save(refreshToken);
        }
        else {
            throw new UsernameLoginExpired("Please provide Your credential again");
        }

    }
    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + "Refresh token was expired , please login again");
        }
        return refreshToken;
    }

}
