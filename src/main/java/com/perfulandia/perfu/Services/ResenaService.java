package com.perfulandia.perfu.Services;

import com.perfulandia.perfu.Model.Resena;
import com.perfulandia.perfu.Repository.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    public List<Resena> listarResenas() {
        return resenaRepository.findAll();
    }

    public Optional<Resena> buscarResenaPorId(int id) {
        return resenaRepository.findById(id);
    }

    public Resena agregarResena(Resena resena) {

        resena.setFecha_resena(new Date());
        return resenaRepository.save(resena);
    }

    public Optional<Resena> actualizarResena(int id, Resena resenaActualizada) {
        return resenaRepository.findById(id)
                .map(resenaExistente -> {

                    resenaExistente.setCalificacion(resenaActualizada.getCalificacion());
                    resenaExistente.setComentario(resenaActualizada.getComentario());

                    return resenaRepository.save(resenaExistente);
                });
    }

    public void eliminarResena(int id) {
        resenaRepository.deleteById(id);
    }
}
