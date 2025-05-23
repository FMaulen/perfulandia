package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Inventario;
import com.perfulandia.perfu.Repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    // Agregar producto
    public String registrarItem(Inventario item){
        inventarioRepository.save(item);
        return "Item registrado correctamente";
    }

    // Get All
    public String listarInventario(){
        String output = "";
        for (Inventario inventario : inventarioRepository.findAll()){
            output += "ID Producto: " + inventario.getId() + "\n";
            output += "Stock Producto: " + inventario.getStock() + "\n";
        }

        if (output.isEmpty()){
            return "No hay items registrados";
        } else{
            return output;
        }
    }

}