package com.perfulandia.perfu.Console;

import java.util.Scanner;

public class LogisticaMenu {
    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\n=== MENÚ LOGÍSTICA ===");
            System.out.println("1. Gestionar Envíos");
            System.out.println("2. Optimizar Rutas");
            System.out.println("3. Actualizar Estado de Pedidos");
            System.out.println("4. Gestionar Proveedores");
            System.out.println("5. Regresar");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch(opcion) {
                case 1:
                    System.out.println("\nGESTIÓN DE ENVÍOS");
                    System.out.println("1. Crear envío");
                    System.out.println("2. Listar envíos");
                    System.out.print("Seleccione: ");
                    int subop1 = scanner.nextInt();
                    scanner.nextLine();
                    // Lógica para gestión de envíos
                    break;

                case 2:
                    System.out.println("\nOPTIMIZACIÓN DE RUTAS");
                    System.out.println("Rutas optimizadas para hoy...");
                    // Lógica para optimización
                    break;

                case 3:
                    System.out.println("\nACTUALIZAR ESTADO DE PEDIDOS");
                    System.out.print("Ingrese ID de pedido: ");
                    long pedidoId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("Actualizando pedido #" + pedidoId);
                    // Lógica para actualización
                    break;

                case 4:
                    System.out.println("\nGESTIÓN DE PROVEEDORES");
                    System.out.println("1. Listar proveedores");
                    System.out.println("2. Agregar proveedor");
                    System.out.print("Seleccione: ");
                    int subop4 = scanner.nextInt();
                    scanner.nextLine();
                    // Lógica para proveedores
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Opción no válida");
            }
        }
    }
}
