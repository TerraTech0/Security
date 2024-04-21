package com.example.security.Api;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
