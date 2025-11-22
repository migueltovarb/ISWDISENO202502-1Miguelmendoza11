package domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "pagos")
public class Pago {
    
    @Id
    private String id;
    
    @DBRef
    private Inscripcion inscripcion;
    
    private Double monto;
    
    @CreatedDate
    private LocalDateTime fechaPago;
    
    private String metodoPago; // TARJETA_CREDITO, TARJETA_DEBITO, PSE, EFECTIVO
    private String estadoPago; // PENDIENTE, PROCESANDO, EXITOSO, FALLIDO, REEMBOLSADO
    private String referenciaBancaria;
    private String numeroTransaccion;
    private String codigoAutorizacion;
    private String motivoFallo;
    
    // Constructor vacío
    public Pago() {
        this.estadoPago = "PENDIENTE";
        this.numeroTransaccion = generarNumeroTransaccion();
    }
    
    // Constructor parametrizado
    public Pago(Inscripcion inscripcion, Double monto, String metodoPago) {
        this.inscripcion = inscripcion;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estadoPago = "PENDIENTE";
        this.numeroTransaccion = generarNumeroTransaccion();
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Inscripcion getInscripcion() { return inscripcion; }
    public void setInscripcion(Inscripcion inscripcion) { this.inscripcion = inscripcion; }
    
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    
    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
    
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
    
    // Métodos del negocio
    public boolean procesarPago() {
        if ("PENDIENTE".equals(this.estadoPago)) {
            this.estadoPago = "PROCESANDO";
            return true;
        }
        return false;
    }
    
    public boolean confirmarPago(String codigoAutorizacion) {
        if ("PROCESANDO".equals(this.estadoPago)) {
            this.estadoPago = "EXITOSO";
            this.codigoAutorizacion = codigoAutorizacion;
            return true;
        }
        return false;
    }
    
    public boolean esPagoExitoso() {
        return "EXITOSO".equals(this.estadoPago);
    }
    
    private String generarNumeroTransaccion() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }
}