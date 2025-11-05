package triangulos;

public class Acutangulo extends TrianguloBase {
	
	private double lado1;
	private double lado2;
	private double lado3;

	@Override
	public double perimetro() {
		// TODO Auto-generated method stub
		return lado1+lado2+lado3;
	}

	public Acutangulo(double lado1, double lado2, double lado3) {
		super();
		this.lado1 = lado1;
		this.lado2 = lado2;
		this.lado3 = lado3;
	}
	
	

}
