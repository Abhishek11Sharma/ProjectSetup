package com.reuse.predefined.exceptions;

import java.net.BindException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.reuse.predefined.dtos.ApiResponse;
import com.reuse.predefined.exceptions.classes.ResourceNotFoundException;
import com.reuse.predefined.exceptions.classes.UserNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.UnexpectedTypeException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse("Resource not found exception !!", false, (exception.getMessage())));
    }

    @ExceptionHandler({ SQLIntegrityConstraintViolationException.class })
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse("SQL integrity violation because of duplicate value !!", false,
                        (exception.getMessage())));
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Resource not found exception !!", false, (exception.getMessage())));
    }

    @ExceptionHandler({ NoSuchElementException.class })
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("No such element exception", false, (exception.getMessage())));
    }

    @ExceptionHandler({ NumberFormatException.class })
    public ResponseEntity<Object> handleNumberFormatException(NumberFormatException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("No such element exception", false, (exception.getMessage())));
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Exception occur", false, (exception.getMessage())));
    }

    @ExceptionHandler({ MissingServletRequestParameterException.class })
    public ResponseEntity<Object> handleMissingServlet(MissingServletRequestParameterException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Servlet request exception", false, (exception.getMessage())));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleNoSuchMethodArgumentException(MethodArgumentNotValidException exception) {
        List<Object> list = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();
            } catch (ClassCastException ex) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            list.add(fieldName + "(" + message + ")");
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Parameter mistach exception !!", false, list));

    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse(" Illegal argument exception occur !!", false,
                        (exception.getMessage())));
    }

    @ExceptionHandler({ BindException.class })
    public ResponseEntity<Object> bindException(BindException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Bind exception !!", false, (exception.getMessage())));
    }

    @ExceptionHandler({ UnexpectedTypeException.class })
    public ResponseEntity<Object> unexpectedTypeException(UnexpectedTypeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Unexpected type  exception !!", false, (exception.getMessage())));
    }

    @ExceptionHandler({ NullPointerException.class })
    public ResponseEntity<Object> nullPointerException(NullPointerException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse("Null pointer exception occur because of no data ", false,
                        (exception.getMessage())));
    }

    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<?> handleAccessDeniedException(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse("Wrong creditional !!", false, (exception.getStackTrace())));
    }

    @ExceptionHandler(DecodingException.class)
    public ResponseEntity<?> handleDecodingException(DecodingException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse("Decoding exception occur !!", false, (exception.getMessage())));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(DataAccessException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Data access exception occur !!", false, (exception.getMessage())));
    }

    @ExceptionHandler({ SignatureException.class })
    public ResponseEntity<?> handleSignatureException(SignatureException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse("Jwt signature exception occur !!", false,
                        (exception.getMessage())));
    }

    @ExceptionHandler({ MalformedJwtException.class })
    public ResponseEntity<?> handleMalformedJwtException(MalformedJwtException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse("Jwt Malformed exception occur !!", false,
                        (exception.getMessage())));
    }

    @ExceptionHandler({ JsonParseException.class })
    public ResponseEntity<?> handleJsonParseException(JsonParseException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse("Jwt parser exception occur !!", false, (exception.getMessage())));
    }

    @ExceptionHandler({ ExpiredJwtException.class })
    public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse("Jwt Expired exception occur !!", false, (exception.getMessage())));
    }

    @ExceptionHandler({ UserNotFoundException.class })
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("Exception occur !!", false, (exception.getMessage())));
    }
}
