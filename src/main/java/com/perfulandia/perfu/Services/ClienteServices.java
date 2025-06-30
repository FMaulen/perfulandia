package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Cliente;
import com.perfulandia.perfu.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServices {

    @Autowired
    private ClienteRepository clienteRepository;


    public Cliente agregarCliente(Cliente cliente){
        cliente.setFecha_registro(new Date());
        return clienteRepository.save(cliente);
    }


    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }


    public Optional<Cliente> obtenerClientePorID(int id){
        return clienteRepository.findById(id);
    }

    public void eliminarClientePorID(int id){
        clienteRepository.deleteById(id);
    }


    public Optional<Cliente> actualizarCliente(int id, Cliente clienteDetails){
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    clienteExistente.setNombre(clienteDetails.getNombre());
                    clienteExistente.setCorreo(clienteDetails.getCorreo());
                    clienteExistente.setTelefono(clienteDetails.getTelefono());
                    clienteExistente.setDireccion(clienteDetails.getDireccion());
                    clienteExistente.setHabilitado(clienteDetails.getHabilitado());

                    return clienteRepository.save(clienteExistente);
                });
    }


    public Optional<Cliente> cambiarEstadoCliente(int id, boolean habilitado){
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setHabilitado(habilitado);
                    return clienteRepository.save(cliente);
                });
    }
}
