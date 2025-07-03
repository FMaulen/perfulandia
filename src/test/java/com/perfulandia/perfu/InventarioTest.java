package com.perfulandia.perfu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.Inventario;
import com.perfulandia.perfu.Repository.InventarioRepository;
import com.perfulandia.perfu.Services.InventarioService;
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
public class InventarioTest {

    @MockitoBean
    private InventarioRepository inventarioRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventarioService inventarioService;


    @Test
    void findAllInventarioRepositoryTest() {
        Inventario itemMock = new Inventario();
        List<Inventario> listaMock = Collections.singletonList(itemMock);
        Mockito.when(inventarioRepository.findAll()).thenReturn(listaMock);

        List<Inventario> inventario = inventarioRepository.findAll();
        assertNotNull(inventario);
        assertTrue(inventario.size() >= 1);
    }


    @Test
    void checkInventarioStockRepositoryTest() {
        Inventario itemMock = new Inventario();
        itemMock.setId(1);
        itemMock.setStock(100);
        Mockito.when(inventarioRepository.findById(1)).thenReturn(Optional.of(itemMock));

        Inventario item = inventarioRepository.findById(1).get();
        assertNotNull(item);
        assertEquals(100, item.getStock());
    }


    @Test
    void getAllInventarioControllerTest() throws Exception {
        Inventario item = new Inventario();
        item.setId(10);
        item.setStock(50);
        List<Inventario> listaMock = Collections.singletonList(item);

        Mockito.when(inventarioService.listarInventario()).thenReturn(listaMock);

        mockMvc.perform(get("/inventario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.inventarioList[0].stock").value(50));
    }


    @Test
    void adjustStockControllerTest() throws Exception {
        Inventario itemActualizado = new Inventario();
        itemActualizado.setId(1);
        itemActualizado.setStock(75);

        Mockito.when(inventarioService.actualizarStock(1, 75)).thenReturn(Optional.of(itemActualizado));

        mockMvc.perform(patch("/inventario/1/stock")
                        .param("stock", "75"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.stock").value(75));
    }
}
