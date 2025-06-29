package com.perfulandia.perfu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.Sucursal;
import com.perfulandia.perfu.Repository.SucursalRepository;
import com.perfulandia.perfu.Services.SucursalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class SucursalTest {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SucursalService sucursalService;


    @Test
    void findAllSucursalesRepositoryTest() {
        List<Sucursal> sucursales = sucursalRepository.findAll();
        assertNotNull(sucursales);
        assertTrue(sucursales.size() >= 1);
    }


    @Test
    void checkSucursalNameRepositoryTest() {
        Sucursal sucursal = sucursalRepository.findById(1).get();
        assertNotNull(sucursal);
        assertEquals("Sucursal Central", sucursal.getNombre());
    }


    @Test
    void getAllSucursalesControllerTest() throws Exception {

        Sucursal sucursalMock = new Sucursal();
        sucursalMock.setId_sucursal(10);
        sucursalMock.setNombre("Sucursal Mock");
        List<Sucursal> listaMock = Collections.singletonList(sucursalMock);


        Mockito.when(sucursalService.listarSucursales()).thenReturn(listaMock);


        mockMvc.perform(get("/sucursales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.sucursalList[0].nombre").value("Sucursal Mock"));
    }


    @Test
    void createSucursalControllerTest() throws Exception {

        Sucursal sucursalSinId = new Sucursal();
        sucursalSinId.setNombre("Nueva Sucursal");
        sucursalSinId.setCorreo("nueva@sucursal.com");

        Sucursal sucursalConId = new Sucursal();
        sucursalConId.setId_sucursal(5);
        sucursalConId.setNombre("Nueva Sucursal");
        sucursalConId.setCorreo("nueva@sucursal.com");


        Mockito.when(sucursalService.registrarSucursal(Mockito.any(Sucursal.class))).thenReturn(sucursalConId);


        mockMvc.perform(post("/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sucursalSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_sucursal").value(5))
                .andExpect(jsonPath("$.nombre").value("Nueva Sucursal"));
    }
}
