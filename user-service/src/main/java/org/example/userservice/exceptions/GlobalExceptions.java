package org.example.userservice.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(HttpServletRequest req
            ,Exception e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setUrl(req.getRequestURI());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(HttpServletRequest req
            ,Exception e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setUrl(req.getRequestURI());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setCode(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

}
