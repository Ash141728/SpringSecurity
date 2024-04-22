package com.inspiron.StudentService.Exception;

public class UsernameLoginExpired extends RuntimeException{
    public UsernameLoginExpired(String message) {
        super(message);
    }
}
