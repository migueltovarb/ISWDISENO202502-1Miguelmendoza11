package triangulos;

public abstract class TrianguloBase {
	
	// Método abstracto - debe ser implementado por clases hijas
	public abstract double perimetro();

	// Método concreto - ya implementado
	public double calcularAreaconHipotenusa(int lado, int hipotenusa) {
	    return (lado * hipotenusa) / 2.0;
	}

}
