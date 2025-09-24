package paqueteCuenta;

public class ProgramaCuenta {
    public static void main(String[] args) {

        Account cuenta1 = new Account("A100", "Carlos", 5000);
        Account cuenta2 = new Account("A200", "Ana", 2000);


        System.out.println(cuenta1);
        System.out.println(cuenta2);


        cuenta1.credit(1500);
        System.out.println("Después de ingresar 1500 en cuenta1: " + cuenta1);

        cuenta1.debit(3000);
        System.out.println("Después de retirar 3000 en cuenta1: " + cuenta1);

        cuenta2.debit(5000); 

        cuenta1.transferTo(cuenta2, 2000);
        System.out.println("Después de transferir 2000 de cuenta1 a cuenta2:");
        System.out.println(cuenta1);
        System.out.println(cuenta2);
    }

}
