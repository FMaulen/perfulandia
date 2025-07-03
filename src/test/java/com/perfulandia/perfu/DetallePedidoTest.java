package com.perfulandia.perfu;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.DetallePedido;
import com.perfulandia.perfu.Repository.DetallePedidoRepository;
import com.perfulandia.perfu.Services.DetallePedidoService;
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
public class DetallePedidoTest {

    @MockitoBean
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DetallePedidoService detallePedidoService;


    @Test
    void findAllDetallesRepositoryTest() {
        DetallePedido detalleMock = new DetallePedido();
        List<DetallePedido> listaMock = Collections.singletonList(detalleMock);
        Mockito.when(detallePedidoRepository.findAll()).thenReturn(listaMock);

        List<DetallePedido> detalles = detallePedidoRepository.findAll();
        assertNotNull(detalles);
        assertTrue(detalles.size() >= 1);
    }


    @Test
    void checkDetalleCantidadRepositoryTest() {
        DetallePedido detalleMock = new DetallePedido();
        detalleMock.setId(1);
        detalleMock.setCantidad(2);
        Mockito.when(detallePedidoRepository.findById(1)).thenReturn(Optional.of(detalleMock));

        DetallePedido detalle = detallePedidoRepository.findById(1).get();
        assertNotNull(detalle);
        assertEquals(2, detalle.getCantidad());
    }


    @Test
    void getAllDetallesControllerTest() throws Exception {
        DetallePedido detalleMock = new DetallePedido();
        detalleMock.setId(10);
        detalleMock.setCantidad(5);
        List<DetallePedido> listaMock = Collections.singletonList(detalleMock);

        Mockito.when(detallePedidoService.listarDetalles()).thenReturn(listaMock);

        mockMvc.perform(get("/detalles-pedido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.detallePedidoList[0].cantidad").value(5));
    }


    @Test
    void createDetalleControllerTest() throws Exception {
        DetallePedido detalleSinId = new DetallePedido();
        detalleSinId.setCantidad(3);
        detalleSinId.setPrecio_unitario(10000);

        DetallePedido detalleConId = new DetallePedido();
        detalleConId.setId(5);
        detalleConId.setCantidad(3);
        detalleConId.setPrecio_unitario(10000);

        Mockito.when(detallePedidoService.agregarDetalle(Mockito.any(DetallePedido.class))).thenReturn(detalleConId);

        mockMvc.perform(post("/detalles-pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(detalleSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.cantidad").value(3));
    }
}
