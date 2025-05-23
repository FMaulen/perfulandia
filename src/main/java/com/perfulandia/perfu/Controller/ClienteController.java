package com.perfulandia.perfu.Controller;

import com.perfulandia.perfu.Model.Cliente;
import com.perfulandia.perfu.Services.ClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
// Como se supone que hago esto
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteServices clienteServices;

    @GetMapping
    public String getClientes(){return clienteServices.listarClientes();}

    @PostMapping
    public String addCliente(@RequestBody Cliente cliente){return clienteServices.agregarCliente(cliente);}

    @GetMapping("/{id}")
    public String getClientePorID(@PathVariable int id){return clienteServices.obtenerClientePorID(id);}

    @DeleteMapping("/{id}")
    public String deleteClientePorID(@PathVariable int id, @RequestBody Cliente cliente){return clienteServices.eliminarClientePorID(id);} // Fix

    @PutMapping("/{id}")
    public String updateClientePorID(@PathVariable int id, @RequestBody Cliente cliente){
        return clienteServices.actualizarCliente(id, cliente);
    }

    @PatchMapping("/{id}/estado")
    public String cambiarEstadoCliente(@PathVariable int id, @RequestParam boolean habilitado){
        return clienteServices.cambiarEstadoCliente(id, habilitado);
    }


}
// No se como lo hice