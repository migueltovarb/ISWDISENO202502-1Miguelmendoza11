package empleados;

public class Empleado {
	private String nombre;
    private String apellidos;
    private String dni;
    private String direccion;
    private int anosAntiguedad;
    private String telefono;
    private double salario;
    private Empleado supervisor;
	
    public Empleado(String nombre, String apellidos, String dni, String direccion, int anosAntiguedad, String telefono,
			double salario, Empleado supervisor) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.direccion = direccion;
		this.anosAntiguedad = anosAntiguedad;
		this.telefono = telefono;
		this.salario = salario;
		this.supervisor = supervisor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getAnosAntiguedad() {
		return anosAntiguedad;
	}

	public void setAnosAntiguedad(int anosAntiguedad) {
		this.anosAntiguedad = anosAntiguedad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public Empleado getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Empleado supervisor) {
		this.supervisor = supervisor;
	}

	@Override
	public String toString() {
		return "Empleado [nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", direccion=" + direccion
				+ ", anosAntiguedad=" + anosAntiguedad + ", telefono=" + telefono + ", salario=" + salario
				+ ", supervisor=" + supervisor + "]";
	}
    
	// Método base para incrementar salario (puede ser sobrescrito)
	public void incrementarSalario() {
	    // Incremento base del 3% si no se especifica
	    double nuevoSalario = salario * 1.03;
	    setSalario(nuevoSalario);
	}

	// Método base para imprimir
	public void imprimir() {
	    System.out.println("=== EMPLEADO ===");
	    System.out.println(toString());
	}

}
