package paqueteAutor;

public class ProgramaAutor {
	   public static void main(String[] args) {
	        Author autor1 = new Author("Gabriel García Márquez", "gabo@example.com", 'm');
	        Author autor2 = new Author("Isabel Allende", "isabel@example.com", 'f');

	        System.out.println("Autor 1: " + autor1);
	        System.out.println("Nombre: " + autor1.getName());
	        System.out.println("Email: " + autor1.getEmail());
	        System.out.println("Género: " + autor1.getGender());

	        System.out.println("\nAutor 2: " + autor2);

	        autor1.setEmail("gabriel.garcia@example.com");
	        System.out.println("\nEmail actualizado de autor1: " + autor1.getEmail());
	        System.out.println("Autor 1 actualizado: " + autor1);
	    }

}
