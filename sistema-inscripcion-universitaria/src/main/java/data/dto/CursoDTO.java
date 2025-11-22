package data.dto;

import jakarta.validation.constraints.*;

public class CursoDTO {
    
    private String id;
    
    @NotNull(message = "El código del curso es obligatorio")
    @Size(min = 3, max = 10, message = "El código debe tener entre 3 y 10 caracteres")
    private String codigo;
    
    @NotNull(message = "El nombre del curso es obligatorio")
    @Size(min = 5, max = 100, message = "El nombre debe tener entre 5 y 100 caracteres")
    private String nombre;
    
    private String descripcion;
    
    @NotNull(message = "Los créditos son obligatorios")
    @Min(value = 1, message = "Mínimo 1 crédito")
    @Max(value = 10, message = "Máximo 10 créditos")
    private Integer creditos;
    
    @NotNull(message = "Los cupos máximos son obligatorios")
    @Min(value = 5, message = "Mínimo 5 cupos")
    @Max(value = 200, message = "Máximo 200 cupos")
    private Integer cuposMaximos;
    
    private Integer cuposOcupados;
    private String modalidad;
    private String departamento;
    private Boolean activo;
    private Boolean esExtension;
    private Double precio;
    
    // Constructor vacío
    public CursoDTO() {
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
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public Boolean getEsExtension() { return esExtension; }
    public void setEsExtension(Boolean esExtension) { this.esExtension = esExtension; }
    
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    
    // Métodos de utilidad
    public Integer getCuposDisponibles() {
        return cuposMaximos != null && cuposOcupados != null ? cuposMaximos - cuposOcupados : 0;
    }
    
    public boolean tieneCuposDisponibles() {
        return getCuposDisponibles() > 0;
    }
    
    public boolean requierePago() {
        return Boolean.TRUE.equals(esExtension) && precio != null && precio > 0;
    }
}