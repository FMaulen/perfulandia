package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.EmpleadoDeVentas;
import com.perfulandia.perfu.Repository.EmpleadoDeVentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoDeVentasService {

    @Autowired
    private EmpleadoDeVentasRepository empleadoRepository;

    public List<EmpleadoDeVentas> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    public Optional<EmpleadoDeVentas> buscarEmpleadoPorId(int id) {
        return empleadoRepository.findById(id);
    }

    public EmpleadoDeVentas agregarEmpleado(EmpleadoDeVentas empleado) {
        empleado.setFecha_contratacion(new Date());
        return empleadoRepository.save(empleado);
    }

    public Optional<EmpleadoDeVentas> actualizarEmpleado(int id, EmpleadoDeVentas empleadoActualizado) {
        return empleadoRepository.findById(id)
                .map(empleadoExistente -> {
                    empleadoExistente.setNombre(empleadoActualizado.getNombre());
                    empleadoExistente.setCorreo(empleadoActualizado.getCorreo());
                    empleadoExistente.setSucursal(empleadoActualizado.getSucursal());
                    empleadoExistente.setSucursalContrato(empleadoActualizado.getSucursalContrato());

                    return empleadoRepository.save(empleadoExistente);
                });
    }

    public void eliminarEmpleado(int id) {
        empleadoRepository.deleteById(id);
    }
}
