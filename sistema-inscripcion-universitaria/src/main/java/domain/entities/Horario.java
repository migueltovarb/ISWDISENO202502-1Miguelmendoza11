package domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.time.LocalTime;

@Document(collection = "horarios")
public class Horario {
    
    @Id
    private String id;
    
    @DBRef
    private Curso curso;
    
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String aula;
    private String edificio;
    
    // Constructor vacío
    public Horario() {}
    
    // Constructor parametrizado
    public Horario(String diaSemana, LocalTime horaInicio, LocalTime horaFin, String aula, String edificio) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.aula = aula;
        this.edificio = edificio;
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
    
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    
    public String getAula() { return aula; }
    public void setAula(String aula) { this.aula = aula; }
    
    public String getEdificio() { return edificio; }
    public void setEdificio(String edificio) { this.edificio = edificio; }
    
    // Métodos del negocio
    public boolean verificarConflicto(Horario otroHorario) {
        if (!this.diaSemana.equals(otroHorario.getDiaSemana())) {
            return false;
        }
        
        return this.horaInicio.isBefore(otroHorario.getHoraFin()) && 
               this.horaFin.isAfter(otroHorario.getHoraInicio());
    }
    
    public boolean validarHoras() {
        return this.horaInicio != null && this.horaFin != null && 
               this.horaInicio.isBefore(this.horaFin);
    }
    
    public boolean esValidoDiaSemana() {
        return diaSemana != null && 
               (diaSemana.equals("LUNES") || diaSemana.equals("MARTES") || 
                diaSemana.equals("MIERCOLES") || diaSemana.equals("JUEVES") || 
                diaSemana.equals("VIERNES") || diaSemana.equals("SABADO"));
    }
}