package com.example.demosecurity.exceptions;

public class UserNotCreatedException extends RuntimeException{
    public UserNotCreatedException(String message) {
        super(message);
    }
}
