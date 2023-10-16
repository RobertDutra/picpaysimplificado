package com.picpaysimplificado.error;

import com.picpaysimplificado.dto.DefaultErrorDTO;
import com.picpaysimplificado.exceptions.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionError extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entidadeNotFoundException(EntityNotFoundException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        DefaultErrorDTO body = DefaultErrorDTO.builder().timestamp(LocalDateTime.now()).status(status).message(e.getMessage()).build();
        return handleExceptionInternal(e, body, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        DefaultErrorDTO body = DefaultErrorDTO.builder().timestamp(LocalDateTime.now()).status(status).message("Usuário já cadastrado!").build();
        return handleExceptionInternal(e, body, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> ConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
            DefaultErrorDTO body = DefaultErrorDTO.builder().timestamp(LocalDateTime.now()).status(status).message("Dados vazios ou não preenchidos!").build();
        return handleExceptionInternal(e, body, new HttpHeaders(), status, request);
    }
}