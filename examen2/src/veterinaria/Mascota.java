package veterinaria;

import java.util.ArrayList;
import java.util.List;

public class Mascota {
    private String nombre;
    private String especie;
    private int edad;
    private Dueno dueno;
    private List<ControlVeterinario> controles;
    
    public Mascota() {
        this.controles = new ArrayList<>();
    }
    
    public Mascota(String nombre, String especie, int edad) {
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.controles = new ArrayList<>();
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public Dueno getDueno() { return dueno; }
    public void setDueno(Dueno dueno) { this.dueno = dueno; }
    public List<ControlVeterinario> getControles() { return controles; }
    
    public void agregarControl(ControlVeterinario control) {
        if (control != null && control.validarCamposObligatorios()) {
            this.controles.add(control);
            control.setMascota(this);
        }
    }
    
    public int contarControlesRealizados() {
        return this.controles.size();
    }
    
    public boolean validarNombreUnico(Dueno dueno) {
        if (dueno == null) return true;
        return dueno.getMascotas().stream()
                .noneMatch(m -> !m.equals(this) && m.getNombre().equalsIgnoreCase(this.nombre));
    }
    
    @Override
    public String toString() {
        return "Mascota{" + "nombre='" + nombre + '\'' + ", especie='" + especie + '\'' + 
               ", edad=" + edad + ", dueno=" + 
               (dueno != null ? dueno.getNombreCompleto() : "Sin dueño") + 
               ", controles=" + controles.size() + '}';
    }
}