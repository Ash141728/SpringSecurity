package com.inspiron.StudentService.Exception;

public class NoStudentFoundException extends RuntimeException{

  public  NoStudentFoundException(String message){
        super(message);
    }
}
