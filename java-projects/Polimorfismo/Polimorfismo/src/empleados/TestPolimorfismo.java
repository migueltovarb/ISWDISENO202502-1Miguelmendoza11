package empleados;

public class TestPolimorfismo {

	public static void main(String[] args) {
		Empleado[] empleados = new Empleado[3];
	    
	    empleados[0] = new Secretario("Ana", "García", "12345678A", "Calle 1", 
	                                  3, "123-456", 25000, null, "Despacho 101", "123-FAX");
	    
	    empleados[1] = new Vendedor("Carlos", "López", "87654321B", "Calle 2", 
	                                5, "234-567", 30000, null, "Toyota-ABC123", "678-901", 
	                                "Norte", "Cliente1,Cliente2", 15.5);
	    
	    empleados[2] = new JefeZona("María", "Ruiz", "11223344C", "Calle 3", 
	                                8, "345-678", 50000, null, "Despacho 200", null, 
	                                "Vendedor1,Vendedor2", "BMW-XYZ789");
	    
	    // Demostrar polimorfismo
	    System.out.println("=== ANTES DEL INCREMENTO ===");
	    for (Empleado emp : empleados) {
	        emp.imprimir();
	        System.out.println();
	    }
	    
	    System.out.println("=== INCREMENTANDO SALARIOS ===");
	    for (Empleado emp : empleados) {
	        System.out.println("Salario anterior: " + emp.getSalario());
	        emp.incrementarSalario();
	        System.out.println("Salario nuevo: " + emp.getSalario());
	        System.out.println();
	    }

	}

}
