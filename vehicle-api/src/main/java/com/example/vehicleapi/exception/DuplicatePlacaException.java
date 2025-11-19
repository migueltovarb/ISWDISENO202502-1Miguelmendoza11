package com.example.vehicleapi.exception;

/**
 * Excepción personalizada para placas duplicadas
 * Se lanza cuando se intenta crear o actualizar un vehículo con una placa que ya existe
 * 
 * @author Miguel
 */
public class DuplicatePlacaException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public DuplicatePlacaException(String message) {
        super(message);
    }
    
    public DuplicatePlacaException(String message, Throwable cause) {
        super(message, cause);
    }
}