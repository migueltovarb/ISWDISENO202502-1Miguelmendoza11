package data.dto;

import jakarta.validation.constraints.*;

public class HorarioDTO {
    
    private String id;
    private String cursoId;
    
    @NotNull(message = "El día de la semana es obligatorio")
    @Pattern(regexp = "^(LUNES|MARTES|MIERCOLES|JUEVES|VIERNES|SABADO)$", 
             message = "Día debe ser LUNES, MARTES, MIERCOLES, JUEVES, VIERNES o SABADO")
    private String diaSemana;
    
    @NotNull(message = "La hora de inicio es obligatoria")
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$", 
             message = "Formato de hora inválido (HH:mm)")
    private String horaInicio;
    
    @NotNull(message = "La hora de fin es obligatoria") 
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$", 
             message = "Formato de hora inválido (HH:mm)")
    private String horaFin;
    
    @NotNull(message = "El aula es obligatoria")
    private String aula;
    
    private String edificio;
    
    // Campos adicionales para respuesta
    private String codigoCurso;
    private String nombreCurso;
    private Integer duracionMinutos;
    
    // Constructores
    public HorarioDTO() {}
    
    public HorarioDTO(String diaSemana, String horaInicio, String horaFin, String aula, String edificio) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.aula = aula;
        this.edificio = edificio;
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getCursoId() { return cursoId; }
    public void setCursoId(String cursoId) { this.cursoId = cursoId; }
    
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    
    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }
    
    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }
    
    public String getAula() { return aula; }
    public void setAula(String aula) { this.aula = aula; }
    
    public String getEdificio() { return edificio; }
    public void setEdificio(String edificio) { this.edificio = edificio; }
    
    public String getCodigoCurso() { return codigoCurso; }
    public void setCodigoCurso(String codigoCurso) { this.codigoCurso = codigoCurso; }
    
    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }
    
    public Integer getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Integer duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    
    // Métodos de validación
    public boolean validarHoras() {
        if (horaInicio == null || horaFin == null) return false;
        
        String[] inicio = horaInicio.split(":");
        String[] fin = horaFin.split(":");
        
        int horaInicioTotal = Integer.parseInt(inicio[0]) * 60 + Integer.parseInt(inicio[1]);
        int horaFinTotal = Integer.parseInt(fin[0]) * 60 + Integer.parseInt(fin[1]);
        
        return horaInicioTotal < horaFinTotal;
    }
    
    public String getHorarioFormateado() {
        return diaSemana + " " + horaInicio + " - " + horaFin + " (" + aula + ")";
    }
}