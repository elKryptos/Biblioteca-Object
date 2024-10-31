package it.objectmethod.biblioteca.exceptions.core;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Jacksonized
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private HttpStatus status;
    private Map<String, String> validationErrors;

    public ErrorDetails(LocalDateTime timestamp, String message, String details, HttpStatus status) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.status = status;
    }

    public ErrorDetails(LocalDateTime timestamp, String message, String details, HttpStatus status, Map<String,String> validationErrors) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.status = status;
        this.validationErrors = validationErrors;
    }

}
