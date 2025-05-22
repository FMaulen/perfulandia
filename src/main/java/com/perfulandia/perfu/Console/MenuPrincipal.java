package com.perfulandia.perfu.Console;

import java.util.Scanner;

public class MenuPrincipal {
    private static final String ADMIN_PASS = "admin123";
    private static final String GERENTE_PASS = "gerente123";
    private static final String EMPLEADO_PASS = "empleado123";
    private static final String LOGISTICA_PASS = "logistica123";

    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            mostrarOpciones();
            int opcion = obtenerOpcion(scanner);

            switch(opcion) {
                case 1 -> autenticar(ADMIN_PASS, "Administrador", AdminMenu::mostrarMenu);
                case 2 -> autenticar(GERENTE_PASS, "Gerente", GerenteMenu::mostrarMenu);
                case 3 -> autenticar(EMPLEADO_PASS, "Empleado", EmpleadoMenu::mostrarMenu);
                case 4 -> autenticar(LOGISTICA_PASS, "Logística", LogisticaMenu::mostrarMenu);
                case 5 -> { System.out.println("Saliendo del sistema..."); return; }
                default -> System.out.println("Opción no válida");
            }
        }
    }

    private static void mostrarOpciones() {
        System.out.println("\n=== SISTEMA PERFULANDIA ===");
        System.out.println("1. Administrador del Sistema");
        System.out.println("2. Gerente de Sucursal");
        System.out.println("3. Empleado de Ventas");
        System.out.println("4. Logística");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int obtenerOpcion(Scanner scanner) {
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    private static void autenticar(String passCorrecta, String rol, Runnable menuAction) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nIngrese contraseña para " + rol + ": ");
        String pass = scanner.nextLine();

        if(pass.equals(passCorrecta)) {
            System.out.println("\nBienvenido " + rol);
            menuAction.run();
        } else {
            System.out.println("Contraseña incorrecta");
        }
    }
}