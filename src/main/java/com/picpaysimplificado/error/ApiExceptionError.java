package com.picpaysimplificado.error;

import com.picpaysimplificado.dto.DefaultErrorDTO;
import com.picpaysimplificado.exceptions.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionError extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entidadeNotFoundException(EntityNotFoundException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        DefaultErrorDTO body = DefaultErrorDTO.builder().timestamp(LocalDateTime.now()).status(status).message(e.getMessage()).build();
        return handleExceptionInternal(e, body, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {
        if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            HttpStatus status = HttpStatus.CONFLICT;
            DefaultErrorDTO body = DefaultErrorDTO.builder().timestamp(LocalDateTime.now()).status(status).message("Dados vazios ou não preenchidos ao salvar no banco!").build();
            return handleExceptionInternal(e, body, new HttpHeaders(), status, request);
        }

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        DefaultErrorDTO body = DefaultErrorDTO.builder().timestamp(LocalDateTime.now()).status(status).message("Usuário já cadastrado!").build();
        return handleExceptionInternal(e, body, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException e, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        DefaultErrorDTO body = DefaultErrorDTO.builder().timestamp(LocalDateTime.now()).status(status).message("Dados vazios ou não preenchidos!").build();
        return handleExceptionInternal(e, body, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);
        DefaultErrorDTO body = DefaultErrorDTO.builder().timestamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST).message(fieldError.getDefaultMessage()).build();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}