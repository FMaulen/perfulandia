package com.perfulandia.perfu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.Cliente;
import com.perfulandia.perfu.Repository.ClienteRepository;
import com.perfulandia.perfu.Services.ClienteServices;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteServices clienteServices;


    @Test
    void findAllClientesRepositoryTest() {
        List<Cliente> clientes = clienteRepository.findAll();
        assertNotNull(clientes);

        assertTrue(clientes.size() >= 1);
    }


    @Test
    void checkClienteNameRepositoryTest() {
        Cliente cliente = clienteRepository.findById(1).get();
        assertNotNull(cliente);
        assertEquals("Cliente de Prueba", cliente.getNombre());
    }


    @Test
    void getAllClientesControllerTest() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setId_cliente(10);
        cliente.setNombre("Cliente Mock");
        List<Cliente> listaMock = Collections.singletonList(cliente);


        Mockito.when(clienteServices.listarClientes()).thenReturn(listaMock);


        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.clienteList[0].nombre").value("Cliente Mock"));
    }


    @Test
    void getClienteByIdControllerTest() throws Exception {

        Cliente clienteMock = new Cliente();
        clienteMock.setId_cliente(1);
        clienteMock.setNombre("Cliente Encontrado");
        clienteMock.setCorreo("encontrado@test.com");


        Mockito.when(clienteServices.obtenerClientePorID(1)).thenReturn(Optional.of(clienteMock));


        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Cliente Encontrado"))
                .andExpect(jsonPath("$.correo").value("encontrado@test.com"));
    }
}
