package sdu.edu.kz.banking_app.exception;

import java.time.LocalDateTime;

public record ErrorDetails(
        LocalDateTime timeStamp,
        String message,
        String details,
        String errorCode
) {
}
