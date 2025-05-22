package com.perfulandia.perfu.Console;

import java.util.Scanner;

public class AdminMenu {
    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Gestionar Usuarios");
            System.out.println("2. Configurar Permisos");
            System.out.println("3. Monitorizar Sistema");
            System.out.println("4. Respaldar Datos");
            System.out.println("5. Regresar");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch(opcion) {
                case 1:
                    System.out.println("\nGESTIÓN DE USUARIOS");
                    // Aquí iría la lógica para llamar a los servicios correspondientes
                    break;
                case 2:
                    System.out.println("\nCONFIGURACIÓN DE PERMISOS");
                    break;
                case 3:
                    System.out.println("\nMONITORIZACIÓN DEL SISTEMA");
                    break;
                case 4:
                    System.out.println("\nRESPALDO DE DATOS");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
}