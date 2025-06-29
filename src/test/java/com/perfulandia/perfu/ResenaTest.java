package com.perfulandia.perfu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.Resena;
import com.perfulandia.perfu.Repository.ResenaRepository;
import com.perfulandia.perfu.Services.ResenaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ResenaTest {

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResenaService resenaService;


    @Test
    void findAllResenasRepositoryTest() {
        List<Resena> resenas = resenaRepository.findAll();
        assertNotNull(resenas);
        assertTrue(resenas.size() >= 1);
    }


    @Test
    void checkResenaCalificacionRepositoryTest() {
        Resena resena = resenaRepository.findById(1).get();
        assertNotNull(resena);
        assertEquals("5", resena.getCalificacion());
    }


    @Test
    void getAllResenasControllerTest() throws Exception {

        Resena resenaMock = new Resena();
        resenaMock.setId_resena(10);
        resenaMock.setComentario("Comentario Mock");
        List<Resena> listaMock = Collections.singletonList(resenaMock);


        Mockito.when(resenaService.listarResenas()).thenReturn(listaMock);


        mockMvc.perform(get("/resenas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.resenaList[0].comentario").value("Comentario Mock"));
    }


    @Test
    void createResenaControllerTest() throws Exception {

        Resena resenaSinId = new Resena();
        resenaSinId.setCalificacion("4");
        resenaSinId.setComentario("Muy buen producto");

        Resena resenaConId = new Resena();
        resenaConId.setId_resena(5);
        resenaConId.setCalificacion("4");
        resenaConId.setComentario("Muy buen producto");


        Mockito.when(resenaService.agregarResena(Mockito.any(Resena.class))).thenReturn(resenaConId);


        mockMvc.perform(post("/resenas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(resenaSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_resena").value(5))
                .andExpect(jsonPath("$.calificacion").value("4"));
    }
}
