package com.perfulandia.perfu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.Envio;
import com.perfulandia.perfu.Repository.EnvioRepository;
import com.perfulandia.perfu.Services.EnvioService;
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
public class EnvioTest {

    @MockitoBean
    private EnvioRepository envioRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EnvioService envioService;


    @Test
    void findAllEnviosRepositoryTest() {
        Envio envioMock = new Envio();
        List<Envio> listaMock = Collections.singletonList(envioMock);
        Mockito.when(envioRepository.findAll()).thenReturn(listaMock);

        List<Envio> envios = envioRepository.findAll();
        assertNotNull(envios);
        assertTrue(envios.size() >= 1);
    }


    @Test
    void checkEnvioEstadoRepositoryTest() {
        Envio envioMock = new Envio();
        envioMock.setId_envio(1);
        envioMock.setEstado("EN_CAMINO");
        Mockito.when(envioRepository.findById(1)).thenReturn(Optional.of(envioMock));

        Envio envio = envioRepository.findById(1).get();
        assertNotNull(envio);
        assertEquals("EN_CAMINO", envio.getEstado());
    }


    @Test
    void getAllEnviosControllerTest() throws Exception {
        Envio envioMock = new Envio();
        envioMock.setId_envio(10);
        envioMock.setEstado("ENTREGADO");
        List<Envio> listaMock = Collections.singletonList(envioMock);

        Mockito.when(envioService.listarEnvios()).thenReturn(listaMock);

        mockMvc.perform(get("/envios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.envioList[0].estado").value("ENTREGADO"));
    }


    @Test
    void createEnvioControllerTest() throws Exception {
        Envio envioSinId = new Envio();
        envioSinId.setDireccion("Otra Calle 456");
        envioSinId.setEstado("PROCESANDO");

        Envio envioConId = new Envio();
        envioConId.setId_envio(5);
        envioConId.setDireccion("Otra Calle 456");
        envioConId.setEstado("PROCESANDO");

        Mockito.when(envioService.agregarEnvio(Mockito.any(Envio.class))).thenReturn(envioConId);

        mockMvc.perform(post("/envios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(envioSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_envio").value(5))
                .andExpect(jsonPath("$.estado").value("PROCESANDO"));
    }
}
