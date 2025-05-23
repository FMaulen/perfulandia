package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Model.DetallePedido;
import com.perfulandia.perfu.Services.EmpleadoDeVentasServices;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ventas")
public class EmpleadoDeVentasController {

    private final EmpleadoDeVentasServices empleadoDeVentasServices;

    // Inyección por constructor
    public EmpleadoDeVentasController(EmpleadoDeVentasServices empleadoDeVentasServices) {
        this.empleadoDeVentasServices = empleadoDeVentasServices;
    }

    @PostMapping
    public String registrarVenta(@RequestBody DetallePedido detallePedido) {
        return empleadoDeVentasServices.registrarVenta(detallePedido);
    }

    @GetMapping
    public String listarVentas() {
        return empleadoDeVentasServices.listarVentas();
    }

    @GetMapping("/{id}")
    public String obtenerVentaPorID(@PathVariable int id) {
        return empleadoDeVentasServices.obtenerVentaPorID(id);
    }
}

