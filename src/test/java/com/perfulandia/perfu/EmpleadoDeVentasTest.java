package com.perfulandia.perfu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.EmpleadoDeVentas;
import com.perfulandia.perfu.Repository.EmpleadoDeVentasRepository;
import com.perfulandia.perfu.Services.EmpleadoDeVentasService;
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
public class EmpleadoDeVentasTest {

    @Autowired
    private EmpleadoDeVentasRepository empleadoRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmpleadoDeVentasService empleadoService;


    @Test
    void findAllEmpleadosRepositoryTest() {
        List<EmpleadoDeVentas> empleados = empleadoRepository.findAll();
        assertNotNull(empleados);
        assertTrue(empleados.size() >= 1);
    }


    @Test
    void checkEmpleadoNameRepositoryTest() {
        EmpleadoDeVentas empleado = empleadoRepository.findById(1).get();
        assertNotNull(empleado);
        assertEquals("Vendedor de Prueba", empleado.getNombre());
    }


    @Test
    void getAllEmpleadosControllerTest() throws Exception {

        EmpleadoDeVentas empleadoMock = new EmpleadoDeVentas();
        empleadoMock.setId_empleado_ventas(10);
        empleadoMock.setNombre("Empleado Mock");
        List<EmpleadoDeVentas> listaMock = Collections.singletonList(empleadoMock);


        Mockito.when(empleadoService.listarEmpleados()).thenReturn(listaMock);


        mockMvc.perform(get("/empleados-ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.empleadoDeVentasList[0].nombre").value("Empleado Mock"));
    }


    @Test
    void createEmpleadoControllerTest() throws Exception {

        EmpleadoDeVentas empleadoSinId = new EmpleadoDeVentas();
        empleadoSinId.setNombre("Nuevo Vendedor");
        empleadoSinId.setCorreo("nuevo.vendedor@test.com");

        EmpleadoDeVentas empleadoConId = new EmpleadoDeVentas();
        empleadoConId.setId_empleado_ventas(5);
        empleadoConId.setNombre("Nuevo Vendedor");
        empleadoConId.setCorreo("nuevo.vendedor@test.com");


        Mockito.when(empleadoService.agregarEmpleado(Mockito.any(EmpleadoDeVentas.class))).thenReturn(empleadoConId);


        mockMvc.perform(post("/empleados-ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(empleadoSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_empleado_ventas").value(5))
                .andExpect(jsonPath("$.nombre").value("Nuevo Vendedor"));
    }
}
