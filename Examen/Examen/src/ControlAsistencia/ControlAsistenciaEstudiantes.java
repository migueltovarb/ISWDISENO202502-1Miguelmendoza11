package ControlAsistencia;

import java.util.Scanner;

public class ControlAsistenciaEstudiantes {
    // Constantes
    public static final int DIAS_SEMANA = 5;
    public static final int NUM_ESTUDIANTES = 4;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] asistencia = new char[NUM_ESTUDIANTES][DIAS_SEMANA];
        boolean datosCargados = false;

        int opcion;
        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Ver asistencia individual");
            System.out.println("2. Ver resumen general");
            System.out.println("3. Volver a registrar");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    if (!datosCargados) {
                        System.out.println("Primero debes ingresar los datos de asistencia.");
                        break;
                    }
                    System.out.print("Ingrese número de estudiante (1 a 4): ");
                    int estudiante = sc.nextInt();
                    if (estudiante >= 1 && estudiante <= NUM_ESTUDIANTES) {
                        int indice = estudiante - 1;
                        System.out.print("Asistencia del estudiante " + estudiante + ": ");
                        for (int d = 0; d < DIAS_SEMANA; d++) {
                            System.out.print(asistencia[indice][d] + " ");
                        }
                        System.out.println();
                    } else {
                        System.out.println("Número inválido.");
                    }
                    break;

                case 2:
                    if (!datosCargados) {
                        System.out.println("Primero debes ingresar los datos de asistencia.");
                        break;
                    }
                    resumenGeneral(asistencia);
                    break;

                case 3:
                    registrarAsistencia(sc, asistencia);
                    datosCargados = true;
                    break;

                case 4:
                    System.out.println("Saliendo del programa.");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
        sc.close();
    }

    public static void registrarAsistencia(Scanner sc, char[][] asistencia) {
        for (int i = 0; i < NUM_ESTUDIANTES; i++) {
            System.out.println("Estudiante " + (i + 1) + ":");
            for (int j = 0; j < DIAS_SEMANA; j++) {
                char valor;
                do {
                    System.out.print(" Día " + (j + 1) + " (P/A): ");
                    valor = Character.toUpperCase(sc.next().charAt(0));
                    if (valor != 'P' && valor != 'A') {
                        System.out.println(" Valor inválido. Use 'P' o 'A'.");
                    }
                } while (valor != 'P' && valor != 'A');
                asistencia[i][j] = valor;
            }
        }
        System.out.println("Asistencia registrada con éxito.");
    }

    public static void resumenGeneral(char[][] asistencia) {
        System.out.println("\n--- RESUMEN GENERAL ---");

        // Total de asistencias por estudiante
        for (int i = 0; i < NUM_ESTUDIANTES; i++) {
            int totalPresente = 0;
            for (int j = 0; j < DIAS_SEMANA; j++) {
                if (asistencia[i][j] == 'P') {
                    totalPresente++;
                }
            }
            System.out.println("Estudiante " + (i + 1) + " asistencias: " + totalPresente);
        }

        // Estudiantes que asistieron todos los días
        System.out.print("Estudiantes que asistieron todos los días: ");
        boolean alguno = false;
        for (int i = 0; i < NUM_ESTUDIANTES; i++) {
            boolean todosPresentes = true;
            for (int j = 0; j < DIAS_SEMANA; j++) {
                if (asistencia[i][j] != 'P') {
                    todosPresentes = false;
                    break;
                }
            }
            if (todosPresentes) {
                System.out.print((i + 1) + " ");
                alguno = true;
            }
        }
        if (!alguno) System.out.print("Ninguno");
        System.out.println();

        // Días con mayor número de ausencias
        int maxAusencias = 0;
        for (int j = 0; j < DIAS_SEMANA; j++) {
            int ausencias = 0;
            for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                if (asistencia[i][j] == 'A') {
                    ausencias++;
                }
            }
            if (ausencias > maxAusencias) {
                maxAusencias = ausencias;
            }
        }

        System.out.print("Día(s) con mayor número de ausencias: ");
        for (int j = 0; j < DIAS_SEMANA; j++) {
            int ausencias = 0;
            for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                if (asistencia[i][j] == 'A') {
                    ausencias++;
                }
            }
            if (ausencias == maxAusencias) {
                System.out.print((j + 1) + " ");
            }
        }
        System.out.println();
    }
}
