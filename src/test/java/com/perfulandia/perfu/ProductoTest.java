package com.perfulandia.perfu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.Producto;
import com.perfulandia.perfu.Repository.ProductoRepository;
import com.perfulandia.perfu.Services.ProductoService;
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
public class ProductoTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;


    @Test
    void findAllProductosRepositoryTest() {
        List<Producto> productos = productoRepository.findAll();
        assertNotNull(productos);
        assertTrue(productos.size() >= 1);
    }


    @Test
    void checkProductoNameRepositoryTest() {
        Producto producto = productoRepository.findById(1).get();
        assertNotNull(producto);
        assertEquals("Perfume de Prueba", producto.getNombre());
    }


    @Test
    void getAllProductosControllerTest() throws Exception {

        Producto productoMock = new Producto();
        productoMock.setId_producto(100);
        productoMock.setNombre("Perfume Mock");
        List<Producto> listaMock = Collections.singletonList(productoMock);


        Mockito.when(productoService.listarProductos()).thenReturn(listaMock);


        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.productoList[0].nombre").value("Perfume Mock"));
    }


    @Test
    void getProductoByIdControllerTest() throws Exception {

        Producto productoMock = new Producto();
        productoMock.setId_producto(1);
        productoMock.setNombre("Producto Encontrado");
        productoMock.setPrecio(15000.0);


        Mockito.when(productoService.buscarProducto(1)).thenReturn(Optional.of(productoMock));


        mockMvc.perform(get("/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Producto Encontrado"))
                .andExpect(jsonPath("$.precio").value(15000.0));
    }
}
