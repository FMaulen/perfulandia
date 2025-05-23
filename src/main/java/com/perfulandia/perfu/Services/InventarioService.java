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
            output += "Nombre Producto: " + inventario.getProducto() + "\n";
            output += "Nombre Sucursal: " + inventario.getSucursal() + "\n";
        }

        if (output.isEmpty()){
            return "No hay items registrados";
        } else{
            return output;
        }
    }

    // Buscar por ID
    public String buscarItemPorID(int id){
        String output = "";
        if (inventarioRepository.existsById(id)){
            Inventario item = inventarioRepository.findById(id).get();
            output += "ID Producto: " + item.getId() + "\n";
            output += "Stock Producto: " + item.getStock() + "\n";
            output += "Nombre Producto: " + item.getProducto() + "\n";
            output += "Nombre Sucursal: " + item.getSucursal() + "\n";
        } else {
            return "No existe un item con ese ID";
        }
        return output;
    }

    // Borrar por ID
    public String eliminarItemPorID(int id){
        if (inventarioRepository.existsById(id)){
            inventarioRepository.deleteById(id);
            return "Item eliminado correctamente";
        } else {
            return "No existe un item con ese ID";
        }
    }

    // Actualizar producto
    public String actualizarItem(int id, Inventario item){
        if (inventarioRepository.existsById(id)){
            Inventario buscado = inventarioRepository.findById(id).get();
            buscado.setStock(item.getStock());
            buscado.setProducto(item.getProducto());
            buscado.setSucursal(item.getSucursal());
            inventarioRepository.save(buscado);
            return "Item actualizado correctamente";
        } else {
            return "No existe un item con ese ID";
        }

    }

    // Actualizar stock
    public String actualizarStock(int id, int stock){
        if (inventarioRepository.existsById(id)){
            Inventario buscado = inventarioRepository.findById(id).get();
            buscado.setStock(stock);
            return "Stock actualizado correctamente";
        } else {
            return "No existe un item con ese ID";
        }
    }





}