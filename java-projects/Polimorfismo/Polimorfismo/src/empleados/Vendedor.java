package empleados;

public class Vendedor extends Empleado {


	private String coche; 
	private String telefonoMovil;
	private String areaVentas;
	private String listaClientes;
	private double porcentajeComisiones;
	
	public Vendedor(String nombre, String apellidos, String dni, String direccion, int anosAntiguedad, String telefono,
			double salario, Empleado supervisor, String coche, String telefonoMovil, String areaVentas,
			String listaClientes, double porcentajeComisiones) {
		super(nombre, apellidos, dni, direccion, anosAntiguedad, telefono, salario, supervisor);
		this.coche = coche;
		this.telefonoMovil = telefonoMovil;
		this.areaVentas = areaVentas;
		this.listaClientes = listaClientes;
		this.porcentajeComisiones = porcentajeComisiones;
	}

	public String getCoche() {
		return coche;
	}

	public void setCoche(String coche) {
		this.coche = coche;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public String getAreaVentas() {
		return areaVentas;
	}

	public void setAreaVentas(String areaVentas) {
		this.areaVentas = areaVentas;
	}

	public String getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(String listaClientes) {
		this.listaClientes = listaClientes;
	}

	public double getPorcentajeComisiones() {
		return porcentajeComisiones;
	}

	public void setPorcentajeComisiones(double porcentajeComisiones) {
		this.porcentajeComisiones = porcentajeComisiones;
	}
	
	public void incrementarSalario() {
	    // Incrementa 10% anual
	    double nuevoSalario = getSalario() * 1.10;
	    setSalario(nuevoSalario);
	}
	
	

	public void imprimir() {
	    System.out.println("=== VENDEDOR ===");
	    System.out.println(toString());
	    System.out.println("Coche: " + coche);
	    System.out.println("Teléfono móvil: " + telefonoMovil);
	    System.out.println("Área de ventas: " + areaVentas);
	    System.out.println("Comisiones: " + porcentajeComisiones + "%");
	}

	// Métodos adicionales según el ejercicio
	public void darDeAltaCliente(String cliente) {
	    // Lógica para agregar cliente
	    System.out.println("Cliente dado de alta: " + cliente);
	}

	public void darDeBajaCliente(String cliente) {
	    // Lógica para dar de baja cliente
	    System.out.println("Cliente dado de baja: " + cliente);
	}

	public void cambiarCoche(String nuevoCoche) {
	    this.coche = nuevoCoche;
	    System.out.println("Coche cambiado a: " + nuevoCoche);
	}
	
	
	
	

}
