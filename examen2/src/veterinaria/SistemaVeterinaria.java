package veterinaria;

import java.util.Date;
import java.util.Scanner;

public class SistemaVeterinaria {
    private static Personal personal;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        personal = new Personal("Dr. María Veterinaria", "12345678", "3001234567", "Veterinario");
        scanner = new Scanner(System.in);
        
        System.out.println("Bienvenido al Sistema de Seguimiento de Mascotas Veterinaria");
        System.out.println("===========================================================");
        
        inicializarDatosPrueba();
        
        int opcion;
        do {
            mostrarMenu();
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                switch (opcion) {
                    case 1: registrarDueno(); break;
                    case 2: registrarMascota(); break;
                    case 3: registrarControl(); break;
                    case 4: consultarHistorialMedico(); break;
                    case 5: generarResumenMascota(); break;
                    case 6: personal.generarReportePorMascota(""); break;
                    case 0: System.out.println("Hasta luego! Gracias por usar el sistema"); break;
                    default: System.out.println("Opción inválida. Por favor seleccione una opción del 0 al 6.");
                }
            } catch (Exception e) {
                System.out.println("Error: Por favor ingrese un número válido");
                scanner.nextLine(); // Limpiar buffer en caso de error
                opcion = -1; // Para que continúe el loop
            }
        } while (opcion != 0);
        
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE SEGUIMIENTO DE MASCOTAS VETERINARIA ===");
        System.out.println("1. Registrar Dueño");
        System.out.println("2. Registrar Mascota");
        System.out.println("3. Registrar Control Veterinario");
        System.out.println("4. Consultar Historial Médico");
        System.out.println("5. Generar Resumen de Mascota");
        System.out.println("6. Generar Reporte General");
        System.out.println("0. Salir");
        System.out.println("========================================================");
        System.out.print("Seleccione una opción: ");
    }
    
    private static void registrarDueno() {
        System.out.println("\n=== REGISTRAR NUEVO DUEÑO ===");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Documento: ");
        String documento = scanner.nextLine().trim();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();
        
        if (nombre.isEmpty() || documento.isEmpty() || telefono.isEmpty()) {
            System.out.println("Error: Todos los campos son obligatorios");
            return;
        }
        
        Dueno dueno = new Dueno(nombre, documento, telefono);
        personal.registrarDueno(dueno);
    } 
 
    
    private static void registrarMascota() {
        System.out.println("\n=== REGISTRAR NUEVA MASCOTA ===");
        System.out.print("Nombre de la mascota: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Especie (perro, gato, etc.): ");
        String especie = scanner.nextLine().trim();
        System.out.print("Edad: ");
        int edad;
        try {
            edad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
        } catch (Exception e) {
            System.out.println("Error: Edad debe ser un número válido");
            scanner.nextLine();
            return;
        }
        
        System.out.print("Documento del dueño: ");
        String documentoDueno = scanner.nextLine().trim();
        
        if (nombre.isEmpty() || especie.isEmpty() || documentoDueno.isEmpty()) {
            System.out.println("Error: Todos los campos son obligatorios");
            return;
        }
        
        Dueno dueno = personal.buscarDueno(documentoDueno);
        if (dueno != null) {
            Mascota mascota = new Mascota(nombre, especie, edad);
            dueno.agregarMascota(mascota);
            personal.registrarMascota(mascota);
        } else {
            System.out.println("Error: Dueño no encontrado. Verifique el documento.");
        }
    }
    
    private static void registrarControl() {
        System.out.println("\n=== REGISTRAR CONTROL VETERINARIO ===");
        System.out.print("Nombre de la mascota: ");
        String nombreMascota = scanner.nextLine().trim();
        
        if (nombreMascota.isEmpty()) {
            System.out.println("Error: El nombre de la mascota es obligatorio");
            return;
        }
        
        System.out.println("Tipos de control sugeridos:");
        System.out.println("   • Vacuna");
        System.out.println("   • Chequeo");
        System.out.println("   • Desparasitación");
        System.out.println("   • Cirugía");
        System.out.println("   • Revisión general");
        System.out.print("Tipo de control: ");
        String tipo = scanner.nextLine().trim();
        
        System.out.print("Observaciones: ");
        String observaciones = scanner.nextLine().trim();
        
        if (tipo.isEmpty() || observaciones.isEmpty()) {
            System.out.println("Error: Todos los campos son obligatorios");
            return;
        }
        
        ControlVeterinario control = new ControlVeterinario(new Date(), tipo, observaciones);
        
        // Buscar mascota y asociar
        boolean mascotaEncontrada = false;
        for (Mascota mascota : personal.getMascotas()) {
            if (mascota.getNombre().equalsIgnoreCase(nombreMascota)) {
                control.setMascota(mascota);
                mascota.agregarControl(control);
                mascotaEncontrada = true;
                break;
            }
        }
        
        if (!mascotaEncontrada) {
            System.out.println("Error: Mascota con nombre " + nombreMascota + " no encontrada");
            return;
        }
        
        personal.registrarControl(control);
    }
    
    private static void consultarHistorialMedico() {
        System.out.println("\n=== CONSULTAR HISTORIAL MÉDICO ===");
        System.out.print("Nombre de la mascota: ");
        String nombreMascota = scanner.nextLine().trim();
        
        if (nombreMascota.isEmpty()) {
            System.out.println("Error: El nombre de la mascota es obligatorio");
            return;
        }
        
        personal.consultarHistorialMedico(nombreMascota);
    }
    
    private static void generarResumenMascota() {
        System.out.println("\n=== GENERAR RESUMEN DE MASCOTA ===");
        System.out.print("Nombre de la mascota: ");
        String nombreMascota = scanner.nextLine().trim();
        
        if (nombreMascota.isEmpty()) {
            System.out.println("Error: El nombre de la mascota es obligatorio");
            return;
        }
        
        personal.generarResumenMascota(nombreMascota);
    }
    
    private static void inicializarDatosPrueba() {
        System.out.println("Inicializando datos de prueba...");
        
        // Crear dueños de prueba
        Dueno dueno1 = new Dueno("Juan Pérez", "12345678", "3001234567");
        Dueno dueno2 = new Dueno("María García", "87654321", "3007654321");
        Dueno dueno3 = new Dueno("Carlos López", "11223344", "3159876543");
        
        personal.registrarDueno(dueno1);
        personal.registrarDueno(dueno2);
        personal.registrarDueno(dueno3);
        
        // Crear mascotas de prueba
        Mascota mascota1 = new Mascota("Max", "Perro", 3);
        Mascota mascota2 = new Mascota("Luna", "Gato", 2);
        Mascota mascota3 = new Mascota("Rocky", "Perro", 5);
        
        dueno1.agregarMascota(mascota1);
        dueno2.agregarMascota(mascota2);
        dueno3.agregarMascota(mascota3);
        
        personal.registrarMascota(mascota1);
        personal.registrarMascota(mascota2);
        personal.registrarMascota(mascota3);
        
        // Crear controles veterinarios de prueba
        ControlVeterinario control1 = new ControlVeterinario(new Date(), "Vacuna", "Vacuna antirrábica aplicada correctamente");
        control1.setMascota(mascota1);
        mascota1.agregarControl(control1);
        personal.registrarControl(control1);
        
        ControlVeterinario control2 = new ControlVeterinario(new Date(), "Chequeo", "Revisión general, animal en buen estado");
        control2.setMascota(mascota2);
        mascota2.agregarControl(control2);
        personal.registrarControl(control2);
        
        ControlVeterinario control3 = new ControlVeterinario(new Date(), "Desparasitación", "Tratamiento contra parásitos internos");
        control3.setMascota(mascota3);
        mascota3.agregarControl(control3);
        personal.registrarControl(control3);
        
        System.out.println("Datos de prueba inicializados correctamente");
        System.out.println("Dueños disponibles:");
        System.out.println("   • Juan Pérez (12345678) - Max (Perro)");
        System.out.println("   • María García (87654321) - Luna (Gato)");
        System.out.println("   • Carlos López (11223344) - Rocky (Perro)");
    }
}