package paqueteAutor;

public class ProgramaLibro {
    public static void main(String[] args) {
        Author autor = new Author("Gabriel García Márquez", "gabo@example.com", 'm');

        book libro1 = new book("Cien años de soledad", autor, 49.99, 100);
        book libro2 = new book("El coronel no tiene quien le escriba", autor, 29.99);

        System.out.println(libro1);
        System.out.println(libro2);

        libro1.setPrice(59.99);
        libro1.setQty(120);

        System.out.println("\nDespués de actualizar:");
        System.out.println(libro1);
    }

}
