package com.perfulandia.perfu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.Pedido;
import com.perfulandia.perfu.Repository.PedidoRepository;
import com.perfulandia.perfu.Services.PedidoService;
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
public class PedidoTest {

    @MockitoBean
    private PedidoRepository pedidoRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService pedidoService;


    @Test
    void findAllPedidosRepositoryTest() {
        Pedido pedidoMock = new Pedido();
        List<Pedido> listaMock = Collections.singletonList(pedidoMock);
        Mockito.when(pedidoRepository.findAll()).thenReturn(listaMock);

        List<Pedido> pedidos = pedidoRepository.findAll();
        assertNotNull(pedidos);
        assertTrue(pedidos.size() >= 1);
    }


    @Test
    void checkPedidoMetodoPagoRepositoryTest() {
        Pedido pedidoMock = new Pedido();
        pedidoMock.setId_pedido(1);
        pedidoMock.setMetodo_pago("TARJETA");
        Mockito.when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedidoMock));

        Pedido pedido = pedidoRepository.findById(1).get();
        assertNotNull(pedido);
        assertEquals("TARJETA", pedido.getMetodo_pago());
    }


    @Test
    void getAllPedidosControllerTest() throws Exception {
        Pedido pedidoMock = new Pedido();
        pedidoMock.setId_pedido(10);
        pedidoMock.setEstado_pedido("ENVIADO");
        List<Pedido> listaMock = Collections.singletonList(pedidoMock);

        Mockito.when(pedidoService.listarPedidos()).thenReturn(listaMock);

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.pedidoList[0].estado_pedido").value("ENVIADO"));
    }


    @Test
    void createPedidoControllerTest() throws Exception {
        Pedido pedidoSinId = new Pedido();
        pedidoSinId.setMetodo_pago("WEBPAY");
        pedidoSinId.setTotal_pedido(50000);

        Pedido pedidoConId = new Pedido();
        pedidoConId.setId_pedido(5);
        pedidoConId.setMetodo_pago("WEBPAY");
        pedidoConId.setTotal_pedido(50000);
        pedidoConId.setEstado_pedido("PENDIENTE");

        Mockito.when(pedidoService.agregarPedido(Mockito.any(Pedido.class))).thenReturn(pedidoConId);

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(pedidoSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_pedido").value(5))
                .andExpect(jsonPath("$.estado_pedido").value("PENDIENTE"));
    }
}
