package veterinaria;

import java.util.ArrayList;
import java.util.List;

public class Dueno {
    private String nombreCompleto;
    private String documento;
    private String telefono;
    private List<Mascota> mascotas;
    
    public Dueno() {
        this.mascotas = new ArrayList<>();
    }
    
    public Dueno(String nombreCompleto, String documento, String telefono) {
        this.nombreCompleto = nombreCompleto;
        this.documento = documento;
        this.telefono = telefono;
        this.mascotas = new ArrayList<>();
    }
    
    // Getters y Setters
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public List<Mascota> getMascotas() { return mascotas; }
    
    public void agregarMascota(Mascota mascota) {
        if (mascota != null && mascota.validarNombreUnico(this)) {
            this.mascotas.add(mascota);
            mascota.setDueno(this);
        }
    }
    
    public boolean validarDatos() {
        return nombreCompleto != null && !nombreCompleto.trim().isEmpty() &&
               documento != null && !documento.trim().isEmpty() &&
               telefono != null && !telefono.trim().isEmpty();
    }
    
    @Override
    public String toString() {
        return "Dueno{" + "nombreCompleto='" + nombreCompleto + '\'' + 
               ", documento='" + documento + '\'' + ", telefono='" + telefono + '\'' + 
               ", mascotas=" + mascotas.size() + '}';
    }
}