package triangulos;

public class Escaleno extends TrianguloBase {
	
	private double lado1;
	private double lado2; 
	private double lado3;

	@Override
	public double perimetro() {
		return lado1 + lado2 + lado3;
	}

	public Escaleno(double lado1, double lado2, double lado3) {
		super();
		this.lado1 = lado1;
		this.lado2 = lado2;
		this.lado3 = lado3;
	}
	
	

}
