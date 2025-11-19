package com.example.vehicleapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Document(collection = "vehicles")
public class Vehicle {
    
    @Id
    private String id;
    
    @NotBlank(message = "La marca es obligatoria")
    @Size(min = 2, max = 50, message = "La marca debe tener entre 2 y 50 caracteres")
    private String marca;
    
    @NotBlank(message = "El modelo es obligatorio")
    @Size(min = 1, max = 50, message = "El modelo debe tener entre 1 y 50 caracteres")
    private String modelo;
    
    @NotNull(message = "El año es obligatorio")
    @Positive(message = "El año debe ser un número positivo")
    private Integer año;
    
    @NotBlank(message = "El color es obligatorio")
    @Size(min = 3, max = 20, message = "El color debe tener entre 3 y 20 caracteres")
    private String color;
    
    @Pattern(regexp = "^[A-Z]{3}-[0-9]{3}$|^[A-Z]{3}[0-9]{3}$", 
             message = "La placa debe tener formato ABC-123 o ABC123")
    @Indexed(unique = true)
    private String placa;
    
    @NotBlank(message = "El tipo de combustible es obligatorio")
    @Pattern(regexp = "GASOLINA|DIESEL|ELECTRICO|HIBRIDO", 
             message = "El tipo debe ser: GASOLINA, DIESEL, ELECTRICO o HIBRIDO")
    private String tipoCombustible;
    
    @Positive(message = "El kilometraje debe ser un número positivo")
    private Double kilometraje;
    
    @Positive(message = "El precio debe ser un número positivo")
    private Double precio;
    
    private String descripcion;
    private Boolean disponible = true;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    
    // Constructor vacío
    public Vehicle() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    // Constructor con parámetros principales
    public Vehicle(String marca, String modelo, Integer año, String color, String placa, 
                  String tipoCombustible, Double kilometraje, Double precio) {
        this();
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.color = color;
        this.placa = placa;
        this.tipoCombustible = tipoCombustible;
        this.kilometraje = kilometraje;
        this.precio = precio;
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public Integer getAño() { return año; }
    public void setAño(Integer año) { this.año = año; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { 
        this.placa = placa; 
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public String getTipoCombustible() { return tipoCombustible; }
    public void setTipoCombustible(String tipoCombustible) { this.tipoCombustible = tipoCombustible; }
    
    public Double getKilometraje() { return kilometraje; }
    public void setKilometraje(Double kilometraje) { 
        this.kilometraje = kilometraje;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { 
        this.precio = precio;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { 
        this.disponible = disponible;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
    
    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", año=" + año +
                ", color='" + color + '\'' +
                ", placa='" + placa + '\'' +
                ", tipoCombustible='" + tipoCombustible + '\'' +
                ", kilometraje=" + kilometraje +
                ", precio=" + precio +
                ", disponible=" + disponible +
                '}';
    }
}