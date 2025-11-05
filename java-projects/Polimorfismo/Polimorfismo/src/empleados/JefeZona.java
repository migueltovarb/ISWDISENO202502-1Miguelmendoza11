package empleados;

public class JefeZona extends Empleado {
	private String despacho;
	private Empleado secretario;
	private String listaVendedores;
	private String coche;
	
	public JefeZona(String nombre, String apellidos, String dni, String direccion, int anosAntiguedad, String telefono,
			double salario, Empleado supervisor, String despacho, Empleado secretario, String listaVendedores,
			String coche) {
		super(nombre, apellidos, dni, direccion, anosAntiguedad, telefono, salario, supervisor);
		this.despacho = despacho;
		this.secretario = secretario;
		this.listaVendedores = listaVendedores;
		this.coche = coche;
	}

	public String getDespacho() {
		return despacho;
	}

	public void setDespacho(String despacho) {
		this.despacho = despacho;
	}

	public Empleado getSecretario() {
		return secretario;
	}

	public void setSecretario(Empleado secretario) {
		this.secretario = secretario;
	}

	public String getListaVendedores() {
		return listaVendedores;
	}

	public void setListaVendedores(String listaVendedores) {
		this.listaVendedores = listaVendedores;
	}

	public String getCoche() {
		return coche;
	}

	public void setCoche(String coche) {
		this.coche = coche;
	}
	
	public void incrementarSalario() {
	    // Incrementa 20% anual
	    double nuevoSalario = getSalario() * 1.20;
	    setSalario(nuevoSalario);
	}

	public void imprimir() {
	    System.out.println("=== JEFE DE ZONA ===");
	    System.out.println(toString());
	    System.out.println("Despacho: " + despacho);
	    System.out.println("Secretario: " + (secretario != null ? secretario.getNombre() : "Sin secretario"));
	    System.out.println("Coche: " + coche);
	}

	// Métodos adicionales según el ejercicio
	public void cambiarSecretario(Empleado nuevoSecretario) {
	    this.secretario = nuevoSecretario;
	    System.out.println("Secretario cambiado");
	}

	public void cambiarCoche(String nuevoCoche) {
	    this.coche = nuevoCoche;
	    System.out.println("Coche cambiado a: " + nuevoCoche);
	}

	public void darDeAltaVendedor(String vendedor) {
	    System.out.println("Vendedor dado de alta: " + vendedor);
	}

	public void darDeBajaVendedor(String vendedor) {
	    System.out.println("Vendedor dado de baja: " + vendedor);
	}
	
	



}
