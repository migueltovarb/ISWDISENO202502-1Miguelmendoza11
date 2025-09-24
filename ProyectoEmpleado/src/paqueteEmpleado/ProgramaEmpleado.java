package paqueteEmpleado;

public class ProgramaEmpleado {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Employee emp = new Employee(1, "Carlos", "Pérez", 3000);

        System.out.println(emp); // toString()
        System.out.println("Nombre completo: " + emp.getName());
        System.out.println("Salario mensual: " + emp.getSalary());
        System.out.println("Salario anual: " + emp.getAnnualSalary());

        emp.raiseSalary(10);
        System.out.println("Salario después del aumento: " + emp.getSalary());

        emp.setSalary(5000);
        System.out.println("Salario cambiado manualmente: " + emp.getSalary());

        System.out.println(emp); 
    

	}

}
