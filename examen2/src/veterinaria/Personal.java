package veterinaria;

import java.util.ArrayList;
import java.util.List;

public class Personal {
    private String nombre;
    private String documento;
    private String telefono;
    private String cargo;
    private List<Dueno> duenos;
    private List<Mascota> mascotas;
    private List<ControlVeterinario> controles;
    
    public Personal() {
        this.duenos = new ArrayList<>();
        this.mascotas = new ArrayList<>();
        this.controles = new ArrayList<>();
    }
    
    public Personal(String nombre, String documento, String telefono, String cargo) {
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.cargo = cargo;
        this.duenos = new ArrayList<>();
        this.mascotas = new ArrayList<>();
        this.controles = new ArrayList<>();
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public List<Mascota> getMascotas() { return mascotas; }
    
    public void registrarDueno(Dueno dueno) {
        if (dueno != null && dueno.validarDatos()) {
            this.duenos.add(dueno);
            System.out.println("Dueño registrado: " + dueno.getNombreCompleto());
        } else {
            System.out.println("Error: Datos del dueño incompletos");
        }
    }
    
    public void registrarMascota(Mascota mascota) {
        if (mascota != null && mascota.getDueno() != null) {
            if (mascota.validarNombreUnico(mascota.getDueno())) {
                this.mascotas.add(mascota);
                System.out.println("Mascota registrada: " + mascota.getNombre());
            } else {
                System.out.println("Error: Ya existe una mascota con ese nombre para este dueño");
            }
        } else {
            System.out.println("Error: Mascota inválida o sin dueño asignado");
        }
    }
    
    public void registrarControl(ControlVeterinario control) {
        if (control != null && control.getMascota() != null && 
            existeMascota(control.getMascota().getNombre()) && 
            control.validarCamposObligatorios()) {
            this.controles.add(control);
            System.out.println("Control veterinario registrado: " + control.getTipoControl());
        } else {
            System.out.println("Error: No se puede registrar el control");
        }
    }
    
    public void generarReportePorMascota(String nombreMascota) {
        System.out.println("\n=== REPORTE GENERAL ===");
        System.out.println("Personal: " + this.nombre + " (" + this.cargo + ")");
        System.out.println("Total de dueños: " + duenos.size());
        System.out.println("Total de mascotas: " + mascotas.size());
        System.out.println("Total de controles: " + controles.size());
        
        if (nombreMascota != null && !nombreMascota.trim().isEmpty()) {
            Mascota mascota = buscarMascota(nombreMascota);
            if (mascota != null) {
                System.out.println("\n=== REPORTE ESPECÍFICO - " + nombreMascota + " ===");
                System.out.println("Dueño: " + mascota.getDueno().getNombreCompleto());
                System.out.println("Especie: " + mascota.getEspecie());
                System.out.println("Edad: " + mascota.getEdad() + " años");
                System.out.println("Controles realizados: " + mascota.contarControlesRealizados());
            }
        }
    }
    
    public List<ControlVeterinario> consultarHistorialMedico(String nombreMascota) {
        Mascota mascota = buscarMascota(nombreMascota);
        if (mascota != null) {
            System.out.println("\n=== HISTORIAL MÉDICO - " + nombreMascota + " ===");
            if (mascota.getControles().isEmpty()) {
                System.out.println("No hay controles veterinarios registrados");
            } else {
                for (ControlVeterinario control : mascota.getControles()) {
                    System.out.println("- " + control.getTipoControl() + " | " + control.getFecha());
                    System.out.println("  Observaciones: " + control.getObservaciones());
                }
            }
            return mascota.getControles();
        } else {
            System.out.println("Mascota no encontrada");
            return new ArrayList<>();
        }
    }
    
    public void generarResumenMascota(String nombreMascota) {
        Mascota mascota = buscarMascota(nombreMascota);
        if (mascota != null) {
            System.out.println("\n=== RESUMEN MASCOTA ===");
            System.out.println("Nombre: " + mascota.getNombre());
            System.out.println("Especie: " + mascota.getEspecie());
            System.out.println("Número de controles realizados: " + mascota.contarControlesRealizados());
            System.out.println("Dueño: " + mascota.getDueno().getNombreCompleto());
            System.out.println("Contacto dueño: " + mascota.getDueno().getTelefono());
        } else {
            System.out.println("Mascota no encontrada");
        }
    }
    
    private boolean existeMascota(String nombreMascota) {
        return buscarMascota(nombreMascota) != null;
    }
    
    private Mascota buscarMascota(String nombreMascota) {
        return mascotas.stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(nombreMascota))
                .findFirst()
                .orElse(null);
    }
    
    public Dueno buscarDueno(String documento) {
        return duenos.stream()
                .filter(d -> d.getDocumento().equals(documento))
                .findFirst()
                .orElse(null);
    }
}