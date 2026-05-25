package com.nihil.clientifyapi.config;

import com.nihil.clientifyapi.models.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionFilter
{

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handleGlobalResponseException(ResponseStatusException ex, HttpServletRequest req){
        String message = ex.getReason() != null ? ex.getReason() : ex.getMessage();
        ExceptionResponse er = new ExceptionResponse(
                false,
                message,
                req.getRequestURI()
        );
        return  ResponseEntity.status(ex.getStatusCode()).body(er);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NoResourceFoundException ex, HttpServletRequest req){
        ExceptionResponse er = new ExceptionResponse(
                false,
                req.getRequestURI() + " Not found",
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleGlobalValidationException(MethodArgumentNotValidException ex, HttpServletRequest req){
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ExceptionResponse er = new ExceptionResponse(
                false,
                message,
                req.getRequestURI()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(er);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGlobalException(Exception ex, HttpServletRequest request) {
        ExceptionResponse er = new ExceptionResponse(
                false,
                "Internal server error",
                request.getRequestURI()
        );
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleGlobalHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest req){
        ExceptionResponse er = new ExceptionResponse(
                false,
                ex.getMessage(),
                req.getRequestURI()
        );
        return ResponseEntity.status(400).body(er);
    }
}
