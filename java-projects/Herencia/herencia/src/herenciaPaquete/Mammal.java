package herenciaPaquete;


public class Mammal extends Animal {

	@Override
	public String toString() {
		return "Mammal [toString()=" + super.toString() + "]";
	}

	public Mammal(String name) {
		super(name);
	}

}
