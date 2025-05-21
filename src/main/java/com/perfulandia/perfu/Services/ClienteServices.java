package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Cliente;
import com.perfulandia.perfu.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Se supone que aqui va la logica pero como le meto la logica
@Service
public class ClienteServices {

    // Nose que hace esto, no tocar
    @Autowired
    private ClienteRepository clienteRepository;

    // para agregar
    public String agregarCliente(Cliente cliente){
        clienteRepository.save(cliente);
        return "Cliente agregado correctamente";
    }

    //GetAll
    public String listarClientes(){
        String output = "";
        for(Cliente cliente : clienteRepository.findAll()){
            output += "ID Cliente: "+cliente.getId_cliente() + "\n";
            output += "Nombre: "+cliente.getNombre() + "\n";
            output += "Correo: "+cliente.getCorreo() + "\n";
            output += "Telefono: "+cliente.getTelefono() + "\n";
            output += "Fecha Registro: "+cliente.getFecha_registro() + "\n";
        }

        if (output.isEmpty()){
            return "No hay clientes";
        }else{
            return output;
        }
    }

    //GetOnePerID
    public String obtenerClientePorID(int id){
        String output = "";
        if(clienteRepository.existsById(id)){
            Cliente cliente = clienteRepository.findById(id).get();
            output += "ID Cliente: "+cliente.getId_cliente() + "\n";
            output += "Nombre: "+cliente.getNombre() + "\n";
            output += "Correo: "+cliente.getCorreo() + "\n";
            output += "Telefono: "+cliente.getTelefono() + "\n";
            output += "Fecha Registro: "+cliente.getFecha_registro() + "\n";
            return output;
        }else{
            return "No existe un cliente con ese ID";
        }
    }

    //Delete by ID
    public String eliminarClientePorID(int id){
        if(clienteRepository.existsById(id)){
            clienteRepository.deleteById(id);
            return "Cliente eliminado correctamente";
        }else{
            return "No existe un cliente con ese ID";
        }
    }

    //Actualizar cliente

    public String actualizarCliente(int id, Cliente cliente){
        if(clienteRepository.existsById(id)){
            Cliente buscado = clienteRepository.findById(id).get();
            buscado.setNombre(cliente.getNombre());
            buscado.setCorreo(cliente.getCorreo());
            buscado.setTelefono(cliente.getTelefono());
            buscado.setFecha_registro(cliente.getFecha_registro());
            buscado.setDireccion(cliente.getDireccion());
            clienteRepository.save(buscado);
            return "Cliente actualizado correctamente";
        }else{
            return "No existe un cliente con ese ID";
        }
    }
}
// Gracias profe por el open source, tengo que leerme la documentacion noma, me quede en pseint