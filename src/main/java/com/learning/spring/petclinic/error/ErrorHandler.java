package com.learning.spring.petclinic.error;

import com.learning.spring.petclinic.error.ErrorResponse;
import com.learning.spring.petclinic.vet.VetNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


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


}
