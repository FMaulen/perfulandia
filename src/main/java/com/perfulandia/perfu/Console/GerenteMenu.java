package com.perfulandia.perfu.Console;

import java.util.Scanner;

public class GerenteMenu {
    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\n=== MENÚ GERENTE ===");
            System.out.println("1. Gestionar Inventario");
            System.out.println("2. Generar Reportes");
            System.out.println("3. Gestionar Sucursal");
            System.out.println("4. Gestionar Pedidos");
            System.out.println("5. Regresar");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch(opcion) {
                case 1:
                    System.out.println("\nGESTIÓN DE INVENTARIO");
                    break;
                case 2:
                    System.out.println("\nGENERACIÓN DE REPORTES");
                    break;
                case 3:
                    System.out.println("\nGESTIÓN DE SUCURSAL");
                    break;
                case 4:
                    System.out.println("\nGESTIÓN DE PEDIDOS");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
}