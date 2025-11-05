package empleados;

public class Secretario extends Empleado {
	private String despacho;
	private String numeroFax;
	public Secretario(String nombre, String apellidos, String dni, String direccion, int anosAntiguedad,
			String telefono, double salario, Empleado supervisor, String despacho, String numeroFax) {
		super(nombre, apellidos, dni, direccion, anosAntiguedad, telefono, salario, supervisor);
		this.despacho = despacho;
		this.numeroFax = numeroFax;
	}
	public String getDespacho() {
		return despacho;
	}
	public void setDespacho(String despacho) {
		this.despacho = despacho;
	}
	public String getNumeroFax() {
		return numeroFax;
	}
	public void setNumeroFax(String numeroFax) {
		this.numeroFax = numeroFax;
	}
	
	public void incrementarSalario() {
	    // Incrementa 5% anual
	    double nuevoSalario = getSalario() * 1.05;
	    setSalario(nuevoSalario);
	}

	public void imprimir() {
	    // Imprime información personal y puesto
	    System.out.println("=== SECRETARIO ===");
	    System.out.println(toString());
	    System.out.println("Despacho: " + despacho);
	    System.out.println("Fax: " + numeroFax);
	}

}
