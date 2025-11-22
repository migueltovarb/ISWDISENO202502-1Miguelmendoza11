package domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "inscripciones")
public class Inscripcion {
    
    @Id
    private String id;
    
    @DBRef
    private Estudiante estudiante;
    
    @DBRef
    private Curso curso;
    
    @CreatedDate
    private LocalDateTime fechaInscripcion;
    
    private String estado; // PENDIENTE, CONFIRMADA, CANCELADA
    private String numeroConfirmacion;
    private String observaciones;
    private Boolean requierePago;
    
    // Constructor vacío
    public Inscripcion() {
        this.estado = "PENDIENTE";
        this.numeroConfirmacion = generarNumeroConfirmacion();
        this.requierePago = false;
    }
    
    // Constructor parametrizado
    public Inscripcion(Estudiante estudiante, Curso curso) {
        this.estudiante = estudiante;
        this.curso = curso;
        this.estado = "PENDIENTE";
        this.numeroConfirmacion = generarNumeroConfirmacion();
        this.requierePago = curso != null ? curso.requierePago() : false;
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
    
    public LocalDateTime getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDateTime fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getNumeroConfirmacion() { return numeroConfirmacion; }
    public void setNumeroConfirmacion(String numeroConfirmacion) { this.numeroConfirmacion = numeroConfirmacion; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public Boolean getRequierePago() { return requierePago; }
    public void setRequierePago(Boolean requierePago) { this.requierePago = requierePago; }
    
    // Métodos del negocio
    public boolean confirmarInscripcion() {
        if ("PENDIENTE".equals(this.estado)) {
            this.estado = "CONFIRMADA";
            return true;
        }
        return false;
    }
    
    public boolean cancelarInscripcion() {
        if ("PENDIENTE".equals(this.estado) || "CONFIRMADA".equals(this.estado)) {
            this.estado = "CANCELADA";
            return true;
        }
        return false;
    }
    
    public boolean puedeSerCancelada() {
        return "PENDIENTE".equals(this.estado) || "CONFIRMADA".equals(this.estado);
    }
    
    private String generarNumeroConfirmacion() {
        return "INS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public boolean estaConfirmada() {
        return "CONFIRMADA".equals(this.estado);
    }
    
    public boolean estaPendiente() {
        return "PENDIENTE".equals(this.estado);
    }
}