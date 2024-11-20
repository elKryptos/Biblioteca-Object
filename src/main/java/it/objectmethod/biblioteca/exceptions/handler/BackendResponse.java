package it.objectmethod.biblioteca.exceptions.handler;

import it.objectmethod.biblioteca.exceptions.NotFoundException;
import it.objectmethod.biblioteca.exceptions.ResourceNotFoundException;
import it.objectmethod.biblioteca.exceptions.ValidationException;
import it.objectmethod.biblioteca.exceptions.core.ErrorDetails;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BackendResponse {

    @ExceptionHandler(ValidationException.class)
    public ResponseWrapper<ErrorDetails> handleValidationException(ValidationException e, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                e.getMessage(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseWrapper<>("Validation error", errorDetails);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseWrapper<ErrorDetails> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = "Errore di validazione. Verifica i dati inviati.";

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage()
                        ));

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST,
                fieldErrors
        );
        return new ResponseWrapper<>("Validation error test", errorDetails);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseWrapper<ErrorDetails> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                e.getMessage(),
                request.getRequestURI(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseWrapper<>("Not found", errorDetails);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseWrapper<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                e.getMessage(),
                request.getRequestURI(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseWrapper<>("Resource not found", errorDetails);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseWrapper<ErrorDetails> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "Il metodo HTTP richiesto non è supportato.",
                request.getRequestURI(),
                HttpStatus.METHOD_NOT_ALLOWED
        );
        return new ResponseWrapper<>("Metodo HTTP non supportato", errorDetails);
    }

    @ExceptionHandler(Exception.class)
    public ResponseWrapper<ErrorDetails> handleException(Exception e, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                e.getMessage(),
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseWrapper<>("Internal server error", errorDetails);
    }

}
