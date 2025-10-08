package veterinaria;

import java.util.Date;

public class ControlVeterinario {
    private Date fecha;
    private String tipoControl;
    private String observaciones;
    private Mascota mascota;
    
    public ControlVeterinario() {}
    
    public ControlVeterinario(Date fecha, String tipoControl, String observaciones) {
        this.fecha = fecha;
        this.tipoControl = tipoControl;
        this.observaciones = observaciones;
    }
    
    // Getters y Setters
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public String getTipoControl() { return tipoControl; }
    public void setTipoControl(String tipoControl) { this.tipoControl = tipoControl; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }
    
    public boolean validarCamposObligatorios() {
        return fecha != null && 
               tipoControl != null && !tipoControl.trim().isEmpty() &&
               observaciones != null && !observaciones.trim().isEmpty();
    }
    
    @Override
    public String toString() {
        return "ControlVeterinario{" + "fecha=" + fecha + 
               ", tipoControl='" + tipoControl + '\'' + 
               ", observaciones='" + observaciones + '\'' + 
               ", mascota=" + (mascota != null ? mascota.getNombre() : "Sin mascota") + '}';
    }
}