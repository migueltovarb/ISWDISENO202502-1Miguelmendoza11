package domain.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "estudiantes")
public class Estudiante extends Usuario {
    
    @Indexed(unique = true)
    private String documento;
    private String carrera;
    private Integer semestre;
    private String telefono;
    private Integer creditos;
    
    // Constructor vacío
    public Estudiante() {
        super();
    }
    
    // Constructor parametrizado
    public Estudiante(String documento, String carrera, String nombre, String apellidos, String email, String password) {
        super(nombre, apellidos, email, password);
        this.documento = documento;
        this.carrera = carrera;
        this.semestre = 1;
        this.creditos = 0;
    }
    
    // Getters y Setters
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
    
    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }
    
    // Implementación de métodos abstractos
    @Override
    public boolean autenticar(String email, String password) {
        return this.email.equals(email) && this.password.equals(password) && this.activo;
    }
    
    @Override
    public boolean validarCredenciales() {
        return this.email != null && !this.email.isEmpty() && 
               this.password != null && this.password.length() >= 8;
    }
    
    // Métodos específicos del negocio
    public boolean puedeInscribirseCurso(int creditosCurso) {
        return this.activo && (this.creditos + creditosCurso <= 20);
    }
    
    public void agregarCreditos(int creditos) {
        this.creditos += creditos;
    }
    
    public boolean validarDocumento() {
        return this.documento != null && this.documento.matches("\\d{8,12}");
    }
}