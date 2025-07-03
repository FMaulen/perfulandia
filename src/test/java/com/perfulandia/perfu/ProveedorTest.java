package com.perfulandia.perfu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.Proveedor;
import com.perfulandia.perfu.Repository.ProveedorRepository;
import com.perfulandia.perfu.Services.ProveedorService;
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
public class ProveedorTest {

    @MockitoBean
    private ProveedorRepository proveedorRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProveedorService proveedorService;


    @Test
    void findAllProveedoresRepositoryTest() {
        Proveedor proveedorMock = new Proveedor();
        List<Proveedor> listaMock = Collections.singletonList(proveedorMock);
        Mockito.when(proveedorRepository.findAll()).thenReturn(listaMock);

        List<Proveedor> proveedores = proveedorRepository.findAll();
        assertNotNull(proveedores);
        assertTrue(proveedores.size() >= 1);
    }


    @Test
    void checkProveedorNameRepositoryTest() {
        Proveedor proveedorMock = new Proveedor();
        proveedorMock.setId_proveedor(1);
        proveedorMock.setNombre("Proveedor Esencias S.A.");
        Mockito.when(proveedorRepository.findById(1)).thenReturn(Optional.of(proveedorMock));

        Proveedor proveedor = proveedorRepository.findById(1).get();
        assertNotNull(proveedor);
        assertEquals("Proveedor Esencias S.A.", proveedor.getNombre());
    }


    @Test
    void getAllProveedoresControllerTest() throws Exception {
        Proveedor proveedorMock = new Proveedor();
        proveedorMock.setId_proveedor(10);
        proveedorMock.setNombre("Proveedor Mock");
        List<Proveedor> listaMock = Collections.singletonList(proveedorMock);

        Mockito.when(proveedorService.listarProveedores()).thenReturn(listaMock);

        mockMvc.perform(get("/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.proveedorList[0].nombre").value("Proveedor Mock"));
    }


    @Test
    void createProveedorControllerTest() throws Exception {
        Proveedor proveedorSinId = new Proveedor();
        proveedorSinId.setNombre("Nuevo Proveedor");
        proveedorSinId.setEmail("nuevo@proveedor.com");

        Proveedor proveedorConId = new Proveedor();
        proveedorConId.setId_proveedor(5);
        proveedorConId.setNombre("Nuevo Proveedor");
        proveedorConId.setEmail("nuevo@proveedor.com");

        Mockito.when(proveedorService.registrarProveedor(Mockito.any(Proveedor.class))).thenReturn(proveedorConId);

        mockMvc.perform(post("/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(proveedorSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_proveedor").value(5))
                .andExpect(jsonPath("$.nombre").value("Nuevo Proveedor"));
    }
}
