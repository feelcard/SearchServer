package org.search.apis.exception;

import org.search.apis.domain.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> handleCustomException(CustomApiException e) {
        ErrorDTO errorDTO = ErrorDTO.getInstance();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setUrl(e.getUrl());
        return ResponseEntity.badRequest().body(errorDTO);
    }


}

