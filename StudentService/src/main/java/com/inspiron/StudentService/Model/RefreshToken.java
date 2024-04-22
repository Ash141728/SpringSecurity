package com.inspiron.StudentService.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
@Data
@Document(collection = "RefreshToken")
public class RefreshToken {

    @Id
    private int id;
    private String token;
    private Instant expiryDate;
    @DBRef
    private Student student;


}
