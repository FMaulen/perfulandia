package com.perfulandia.perfu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.Logistica;
import com.perfulandia.perfu.Model.Proveedor;
import com.perfulandia.perfu.Repository.LogisticaRepository;
import com.perfulandia.perfu.Services.LogisticaService;
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
public class LogisticaTest {

    @MockitoBean
    private LogisticaRepository logisticaRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LogisticaService logisticaService;


    @Test
    void findAllLogisticasRepositoryTest() {
        Logistica logisticaMock = new Logistica();
        List<Logistica> listaMock = Collections.singletonList(logisticaMock);
        Mockito.when(logisticaRepository.findAll()).thenReturn(listaMock);

        List<Logistica> logisticas = logisticaRepository.findAll();
        assertNotNull(logisticas);
        assertTrue(logisticas.size() >= 1);
    }


    @Test
    void checkLogisticaNameAndRelationRepositoryTest() {
        Logistica logisticaMock = new Logistica();
        logisticaMock.setId_logistica(1);
        logisticaMock.setNombre("LogiPerfume");
        logisticaMock.setProveedores(Collections.singleton(new Proveedor()));
        Mockito.when(logisticaRepository.findById(1)).thenReturn(Optional.of(logisticaMock));

        Logistica logistica = logisticaRepository.findById(1).get();
        assertNotNull(logistica);
        assertEquals("LogiPerfume", logistica.getNombre());
        assertEquals(1, logistica.getProveedores().size());
    }


    @Test
    void getAllLogisticasControllerTest() throws Exception {
        Logistica logisticaMock = new Logistica();
        logisticaMock.setId_logistica(10);
        logisticaMock.setNombre("Logistica Mock");
        List<Logistica> listaMock = Collections.singletonList(logisticaMock);

        Mockito.when(logisticaService.listarLogisticas()).thenReturn(listaMock);

        mockMvc.perform(get("/logisticas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.logisticaList[0].nombre").value("Logistica Mock"));
    }


    @Test
    void createLogisticaControllerTest() throws Exception {
        Logistica logisticaSinId = new Logistica();
        logisticaSinId.setNombre("Nueva Logistica");
        logisticaSinId.setCorreo("nueva@logistica.com");

        Logistica logisticaConId = new Logistica();
        logisticaConId.setId_logistica(5);
        logisticaConId.setNombre("Nueva Logistica");
        logisticaConId.setCorreo("nueva@logistica.com");

        Mockito.when(logisticaService.agregarLogistica(Mockito.any(Logistica.class))).thenReturn(logisticaConId);

        mockMvc.perform(post("/logisticas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(logisticaSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_logistica").value(5))
                .andExpect(jsonPath("$.nombre").value("Nueva Logistica"));
    }
}
