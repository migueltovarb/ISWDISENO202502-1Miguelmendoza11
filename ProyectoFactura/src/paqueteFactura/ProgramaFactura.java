package paqueteFactura;

public class ProgramaFactura {
    public static void main(String[] args) {
        InvoiceItem item1 = new InvoiceItem("A101", "Laptop", 2, 1500.0);

        System.out.println(item1); // toString()
        System.out.println("ID: " + item1.getID());
        System.out.println("Descripción: " + item1.getDesc());
        System.out.println("Cantidad: " + item1.getQty());
        System.out.println("Precio unitario: " + item1.getUnitPrice());
        System.out.println("Total: " + item1.getTotal());

        item1.setQty(3);
        item1.setUnitPrice(1400.0);

        System.out.println("\nDespués de actualizar:");
        System.out.println(item1);
        System.out.println("Nuevo total: " + item1.getTotal());
    }


}
