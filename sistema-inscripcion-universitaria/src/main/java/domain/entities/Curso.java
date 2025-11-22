package domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.LocalDateTime;

@Document(collection = "cursos")
public class Curso {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String codigo;
    private String nombre;
    private String descripcion;
    private Integer creditos;
    private Integer cuposMaximos;
    private Integer cuposOcupados;
    private String modalidad; // PRESENCIAL, VIRTUAL, HIBRIDO
    private String departamento;
    private String facultad;
    private Boolean activo;
    private Boolean esExtension; // Para pagos
    private Double precio; // Para cursos de extensión
    
    @CreatedDate
    private LocalDateTime fechaCreacion;
    
    // Constructor vacío
    public Curso() {
        this.cuposOcupados = 0;
        this.activo = true;
        this.esExtension = false;
    }
    
    // Constructor parametrizado
    public Curso(String codigo, String nombre, Integer creditos, Integer cuposMaximos, String modalidad, String departamento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.cuposMaximos = cuposMaximos;
        this.modalidad = modalidad;
        this.departamento = departamento;
        this.cuposOcupados = 0;
        this.activo = true;
        this.esExtension = false;
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }
    
    public Integer getCuposMaximos() { return cuposMaximos; }
    public void setCuposMaximos(Integer cuposMaximos) { this.cuposMaximos = cuposMaximos; }
    
    public Integer getCuposOcupados() { return cuposOcupados; }
    public void setCuposOcupados(Integer cuposOcupados) { this.cuposOcupados = cuposOcupados; }
    
    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }
    
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    
    public String getFacultad() { return facultad; }
    public void setFacultad(String facultad) { this.facultad = facultad; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public Boolean getEsExtension() { return esExtension; }
    public void setEsExtension(Boolean esExtension) { this.esExtension = esExtension; }
    
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    // Métodos del negocio
    public boolean tieneCuposDisponibles() {
        return this.cuposOcupados < this.cuposMaximos;
    }
    
    public Integer getCuposDisponibles() {
        return this.cuposMaximos - this.cuposOcupados;
    }
    
    public boolean ocuparCupo() {
        if (tieneCuposDisponibles()) {
            this.cuposOcupados++;
            return true;
        }
        return false;
    }
    
    public boolean liberarCupo() {
        if (this.cuposOcupados > 0) {
            this.cuposOcupados--;
            return true;
        }
        return false;
    }
    
    public boolean validarCodigo() {
        return this.codigo != null && this.codigo.matches("^[A-Z]{2,4}[0-9]{3,4}$");
    }
    
    public boolean requierePago() {
        return this.esExtension && this.precio != null && this.precio > 0;
    }
}
