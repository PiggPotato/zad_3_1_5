package com.example.demosecurity.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserGlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException NotFoundException) {
        UserErrorResponse userErrorResponse = new UserErrorResponse("User with this id - not found!",
                System.currentTimeMillis());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler // тут без user userNotCreatedException
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException NotCreatedException) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(NotCreatedException.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(Exception e) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
