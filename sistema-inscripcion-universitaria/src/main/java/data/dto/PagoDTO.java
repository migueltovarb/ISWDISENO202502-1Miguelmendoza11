package data.dto;

import jakarta.validation.constraints.*;

public class PagoDTO {
    
    private String id;
    
    @NotNull(message = "El ID de inscripción es obligatorio")
    private String inscripcionId;
    
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "1000.0", message = "El monto mínimo es $1,000")
    private Double monto;
    
    private String fechaPago;
    
    @NotNull(message = "El método de pago es obligatorio")
    @Pattern(regexp = "^(TARJETA_CREDITO|TARJETA_DEBITO|PSE|EFECTIVO)$", 
             message = "Método debe ser TARJETA_CREDITO, TARJETA_DEBITO, PSE o EFECTIVO")
    private String metodoPago;
    
    @Pattern(regexp = "^(PENDIENTE|PROCESANDO|EXITOSO|FALLIDO|REEMBOLSADO)$",
             message = "Estado debe ser PENDIENTE, PROCESANDO, EXITOSO, FALLIDO o REEMBOLSADO")
    private String estadoPago;
    
    private String referenciaBancaria;
    private String numeroTransaccion;
    private String codigoAutorizacion;
    private String motivoFallo;
    
    // Campos adicionales para respuesta
    private String nombreEstudiante;
    private String nombreCurso;
    private String codigoCurso;
    private String numeroConfirmacionInscripcion;
    
    // Constructores
    public PagoDTO() {
        this.estadoPago = "PENDIENTE";
    }
    
    public PagoDTO(String inscripcionId, Double monto, String metodoPago) {
        this();
        this.inscripcionId = inscripcionId;
        this.monto = monto;
        this.metodoPago = metodoPago;
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getInscripcionId() { return inscripcionId; }
    public void setInscripcionId(String inscripcionId) { this.inscripcionId = inscripcionId; }
    
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    
    public String getFechaPago() { return fechaPago; }
    public void setFechaPago(String fechaPago) { this.fechaPago = fechaPago; }
    
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    
    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
    
    public String getReferenciaBancaria() { return referenciaBancaria; }
    public void setReferenciaBancaria(String referenciaBancaria) { this.referenciaBancaria = referenciaBancaria; }
    
    public String getNumeroTransaccion() { return numeroTransaccion; }
    public void setNumeroTransaccion(String numeroTransaccion) { this.numeroTransaccion = numeroTransaccion; }
    
    public String getCodigoAutorizacion() { return codigoAutorizacion; }
    public void setCodigoAutorizacion(String codigoAutorizacion) { this.codigoAutorizacion = codigoAutorizacion; }
    
    public String getMotivoFallo() { return motivoFallo; }
    public void setMotivoFallo(String motivoFallo) { this.motivoFallo = motivoFallo; }
    
    public String getNombreEstudiante() { return nombreEstudiante; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }
    
    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }
    
    public String getCodigoCurso() { return codigoCurso; }
    public void setCodigoCurso(String codigoCurso) { this.codigoCurso = codigoCurso; }
    
    public String getNumeroConfirmacionInscripcion() { return numeroConfirmacionInscripcion; }
    public void setNumeroConfirmacionInscripcion(String numeroConfirmacionInscripcion) { 
        this.numeroConfirmacionInscripcion = numeroConfirmacionInscripcion; 
    }
    
    // Métodos de validación
    public boolean esMontoValido() {
        return monto != null && monto >= 1000.0;
    }
    
    public boolean esPagoExitoso() {
        return "EXITOSO".equals(estadoPago);
    }
    
    public boolean esPagoFallido() {
        return "FALLIDO".equals(estadoPago);
    }
    
    public boolean estaPendiente() {
        return "PENDIENTE".equals(estadoPago);
    }
    
    public boolean puedeSerReembolsado() {
        return "EXITOSO".equals(estadoPago);
    }
}