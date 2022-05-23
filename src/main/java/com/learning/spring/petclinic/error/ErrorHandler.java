package com.learning.spring.petclinic.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
public class ErrorHandler {

    /**
     * Handles Vet exception and returns a response entity with error 404
     *
     * @param e The exception object that was thrown.
     * @return A ResponseEntity object is being returned.
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handler(VetNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handler(OwnerNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handler(MethodArgumentNotValidException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ErrorMessages.INVALID_INPUT);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles general exceptions and returns a response entity with error 400
     *
     * @param e The exception object that was thrown.
     * @return A ResponseEntity object is being returned.
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handler(Exception e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ErrorResponse> reply(String message, HttpStatus httpStatus){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(httpStatus.value());
        errorResponse.setMessage(message);
        return new ResponseEntity<>(errorResponse,httpStatus);
    }

}
