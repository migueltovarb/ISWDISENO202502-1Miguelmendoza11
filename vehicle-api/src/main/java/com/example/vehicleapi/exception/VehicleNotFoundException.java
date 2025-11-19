package com.example.vehicleapi.exception;

/**
 * Excepción personalizada para cuando no se encuentra un vehículo
 * Se lanza cuando se busca un vehículo por ID o placa y no existe
 * 
 * @author Miguel
 */
public class VehicleNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public VehicleNotFoundException(String message) {
        super(message);
    }
    
    public VehicleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}