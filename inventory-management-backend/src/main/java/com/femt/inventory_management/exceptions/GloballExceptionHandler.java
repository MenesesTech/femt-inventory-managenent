package com.femt.inventory_management.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GloballExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GloballExceptionHandler.class);

    // ===== DIMENSION =====
    @ExceptionHandler(DimensionNotFoundException.class)
    public ResponseEntity<?> handleDimensionNotFound(DimensionNotFoundException ex) {
        logger.warn("No encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(DimensionValidationException.class)
    public ResponseEntity<?> handleDimensionValidation(DimensionValidationException ex) {
        logger.error("Error de validación en el campo {}: {}", ex.getArg(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "campo", ex.getArg()
                ));
    }

    // ===== KIT SERIE =====
    @ExceptionHandler(KitSerieNotFoundException.class)
    public ResponseEntity<?> handleKitSerieNotFound(KitSerieNotFoundException ex) {
        logger.warn("Kit serie no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(KitSerieValidationException.class)
    public ResponseEntity<?> handleKitSerieValidation(KitSerieValidationException ex) {
        logger.error("Error de validación (KitSerie) en {}: {}", ex.getArg(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "campo", ex.getArg()
                ));
    }

    // ===== GENERAL =====
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        logger.error("Error inesperado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error interno del servidor"));
    }
}