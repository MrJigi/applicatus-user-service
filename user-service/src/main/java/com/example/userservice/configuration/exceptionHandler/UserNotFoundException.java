package com.example.userservice.configuration.exceptionHandler;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){super(message);}
}
