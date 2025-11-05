package triangulos;

public class TestTriangulos {

	public static void main(String[] args) {
		 // Crear triángulo escaleno
	    Escaleno escaleno = new Escaleno(3.0, 4.0, 5.0);
	    
	    // Crear triángulo acutángulo  
	    Acutangulo acutangulo = new Acutangulo(6.0, 8.0, 10.0);
	    
	    // Probar métodos abstractos implementados
	    System.out.println("=== ESCALENO ===");
	    System.out.println("Perímetro: " + escaleno.perimetro());
	    System.out.println("Área con hipotenusa: " + escaleno.calcularAreaconHipotenusa(3, 5));
	    
	    System.out.println("\n=== ACUTÁNGULO ===");
	    System.out.println("Perímetro: " + acutangulo.perimetro());
	    System.out.println("Área con hipotenusa: " + acutangulo.calcularAreaconHipotenusa(6, 10));
	}
	

}
