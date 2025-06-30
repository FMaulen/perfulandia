package com.perfulandia.perfu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.GerenteSucursal;
import com.perfulandia.perfu.Repository.GerenteSucursalRepository;
import com.perfulandia.perfu.Services.GerenteSucursalService;
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
public class GerenteSucursalTest {

    @Autowired
    private GerenteSucursalRepository gerenteSucursalRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GerenteSucursalService gerenteSucursalService;


    @Test
    void findAllGerentesRepositoryTest() {
        List<GerenteSucursal> gerentes = gerenteSucursalRepository.findAll();
        assertNotNull(gerentes);
        assertTrue(gerentes.size() >= 1);
    }


    @Test
    void checkGerenteNameRepositoryTest() {

        GerenteSucursal gerente = gerenteSucursalRepository.findById(1L).get();
        assertNotNull(gerente);
        assertEquals("Gerente de Prueba", gerente.getNombre());
    }


    @Test
    void getAllGerentesControllerTest() throws Exception {

        GerenteSucursal gerente = new GerenteSucursal();
        gerente.setId_gerente_sucursal(10L);
        gerente.setNombre("Gerente Mock");
        List<GerenteSucursal> listaMock = Collections.singletonList(gerente);


        Mockito.when(gerenteSucursalService.listarGerentes()).thenReturn(listaMock);


        mockMvc.perform(get("/gerentes-sucursal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.gerenteSucursalList[0].nombre").value("Gerente Mock"));
    }


    @Test
    void createGerenteControllerTest() throws Exception {

        GerenteSucursal gerenteSinId = new GerenteSucursal();
        gerenteSinId.setNombre("Nuevo Gerente");
        gerenteSinId.setCorreo("nuevogerente@test.com");

        GerenteSucursal gerenteConId = new GerenteSucursal();
        gerenteConId.setId_gerente_sucursal(5L);
        gerenteConId.setNombre("Nuevo Gerente");
        gerenteConId.setCorreo("nuevogerente@test.com");


        Mockito.when(gerenteSucursalService.agregarGerente(Mockito.any(GerenteSucursal.class))).thenReturn(gerenteConId);


        mockMvc.perform(post("/gerentes-sucursal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(gerenteSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_gerente_sucursal").value(5))
                .andExpect(jsonPath("$.nombre").value("Nuevo Gerente"));
    }
}
