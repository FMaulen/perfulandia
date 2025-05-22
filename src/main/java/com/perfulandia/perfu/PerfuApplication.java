package com.perfulandia.perfu;

import com.perfulandia.perfu.Console.MenuPrincipal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PerfuApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PerfuApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("=== SISTEMA PERFULANDIA ===");
		System.out.println("Iniciando interfaz de consola...\n");
		MenuPrincipal.mostrarMenu();
	}
}