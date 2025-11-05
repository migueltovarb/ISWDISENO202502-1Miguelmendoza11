package herenciaPaquete;

public class Test {
	 public static void main(String[] args) {
	        Cat cat = new Cat("Michi");
	        Dog dog1 = new Dog("Firulais");
	        Dog dog2 = new Dog("Rex");
	        
	        cat.greets();      // "Meow"
	        dog1.greets();     // "Woof"
	        dog1.greets(dog2); // "Woooof"
	    }

}
