package data.dto;

import jakarta.validation.constraints.*;

public class InscripcionDTO {
    
    private String id;
    
    @NotNull(message = "El ID del estudiante es obligatorio")
    private String estudianteId;
    
    @NotNull(message = "El ID del curso es obligatorio") 
    private String cursoId;
    
    private String fechaInscripcion;
    
    @Pattern(regexp = "^(PENDIENTE|CONFIRMADA|CANCELADA)$", 
             message = "Estado debe ser PENDIENTE, CONFIRMADA o CANCELADA")
    private String estado;
    
    private String numeroConfirmacion;
    
    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    private String observaciones;
    
    private Boolean requierePago;
    
    // Campos adicionales para respuesta
    private String nombreEstudiante;
    private String apellidosEstudiante;
    private String documentoEstudiante;
    private String nombreCurso;
    private String codigoCurso;
    private Integer creditosCurso;
    private Double precioCurso;
    
    // Constructores
    public InscripcionDTO() {
        this.estado = "PENDIENTE";
        this.requierePago = false;
    }
    
    public InscripcionDTO(String estudianteId, String cursoId) {
        this();
        this.estudianteId = estudianteId;
        this.cursoId = cursoId;
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getEstudianteId() { return estudianteId; }
    public void setEstudianteId(String estudianteId) { this.estudianteId = estudianteId; }
    
    public String getCursoId() { return cursoId; }
    public void setCursoId(String cursoId) { this.cursoId = cursoId; }
    
    public String getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(String fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getNumeroConfirmacion() { return numeroConfirmacion; }
    public void setNumeroConfirmacion(String numeroConfirmacion) { this.numeroConfirmacion = numeroConfirmacion; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public Boolean getRequierePago() { return requierePago; }
    public void setRequierePago(Boolean requierePago) { this.requierePago = requierePago; }
    
    public String getNombreEstudiante() { return nombreEstudiante; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }
    
    public String getApellidosEstudiante() { return apellidosEstudiante; }
    public void setApellidosEstudiante(String apellidosEstudiante) { this.apellidosEstudiante = apellidosEstudiante; }
    
    public String getDocumentoEstudiante() { return documentoEstudiante; }
    public void setDocumentoEstudiante(String documentoEstudiante) { this.documentoEstudiante = documentoEstudiante; }
    
    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }
    
    public String getCodigoCurso() { return codigoCurso; }
    public void setCodigoCurso(String codigoCurso) { this.codigoCurso = codigoCurso; }
    
    public Integer getCreditosCurso() { return creditosCurso; }
    public void setCreditosCurso(Integer creditosCurso) { this.creditosCurso = creditosCurso; }
    
    public Double getPrecioCurso() { return precioCurso; }
    public void setPrecioCurso(Double precioCurso) { this.precioCurso = precioCurso; }
    
    // Métodos de validación
    public boolean esEstadoValido() {
        return "PENDIENTE".equals(estado) || "CONFIRMADA".equals(estado) || "CANCELADA".equals(estado);
    }
    
    public boolean puedeSerCancelada() {
        return "PENDIENTE".equals(estado) || "CONFIRMADA".equals(estado);
    }
    
    public boolean estaConfirmada() {
        return "CONFIRMADA".equals(estado);
    }
    
    public String getNombreCompletoEstudiante() {
        return nombreEstudiante != null && apellidosEstudiante != null ? 
               nombreEstudiante + " " + apellidosEstudiante : "";
    }
}