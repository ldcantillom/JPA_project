package org.example.airport.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private String message;
    private int status;
    private LocalDateTime timestamp;
}
