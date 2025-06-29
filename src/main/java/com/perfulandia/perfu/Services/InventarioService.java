package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Inventario;
import com.perfulandia.perfu.Repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public Inventario registrarItem(Inventario item){
        return inventarioRepository.save(item);
    }

    public List<Inventario> listarInventario(){
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> buscarItemPorID(int id){
        return inventarioRepository.findById(id);
    }

    public void eliminarItemPorID(int id){
        inventarioRepository.deleteById(id);
    }

    public Optional<Inventario> actualizarItem(int id, Inventario itemDetails){
        return inventarioRepository.findById(id)
                .map(itemExistente -> {
                    itemExistente.setStock(itemDetails.getStock());
                    itemExistente.setProducto(itemDetails.getProducto());
                    itemExistente.setSucursal(itemDetails.getSucursal());
                    return inventarioRepository.save(itemExistente);
                });
    }

    public Optional<Inventario> actualizarStock(int id, int stock){
        return inventarioRepository.findById(id)
                .map(itemExistente -> {
                    itemExistente.setStock(stock);
                    return inventarioRepository.save(itemExistente);
                });
    }
}
