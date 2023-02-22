package com.sparta.serviceteam4444.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class RestApiExceptionHandler {
//
//    @ExceptionHandler(value = { Exception.class })
//    public ResponseEntity<Object> handleApiRequestException(Exception ex) {
//        RestApiException restApiException = new RestApiException();
//        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST.value());
//        restApiException.setMsg(ex.getMessage());
//
//        return new ResponseEntity<>(
//                restApiException,
//                HttpStatus.BAD_REQUEST
//        );
//    }
//}
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CheckApiException.class})
    public ResponseEntity<Object> handleApiRequestException(CheckApiException ex) {
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(new RestApiException(ex.getErrorCode()));
    }
}