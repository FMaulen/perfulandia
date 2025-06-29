package com.perfulandia.perfu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.Permiso;
import com.perfulandia.perfu.Repository.PermisoRepository;
import com.perfulandia.perfu.Services.PermisoService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PermisoTest {

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PermisoService permisoService;


    @Test
    void findAllPermisosRepositoryTest() {
        List<Permiso> permisos = permisoRepository.findAll();
        assertNotNull(permisos);

        assertTrue(permisos.size() >= 2);
    }


    @Test
    void checkPermisoNameRepositoryTest() {
        Permiso permiso = permisoRepository.findById(1).get();
        assertNotNull(permiso);
        assertEquals("GESTIONAR_USUARIOS", permiso.getNombre_permiso());
    }


    @Test
    void getAllPermisosControllerTest() throws Exception {

        Permiso permiso = new Permiso();
        permiso.setId_permiso(10);
        permiso.setNombre_permiso("PERMISO_MOCK");
        List<Permiso> listaMock = Collections.singletonList(permiso);


        Mockito.when(permisoService.listarTodos()).thenReturn(listaMock);


        mockMvc.perform(get("/permisos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.permisoList[0].nombre_permiso").value("PERMISO_MOCK"));
    }


    @Test
    void createPermisoControllerTest() throws Exception {

        Permiso permisoSinId = new Permiso();
        permisoSinId.setNombre_permiso("NUEVO_PERMISO");

        Permiso permisoConId = new Permiso();
        permisoConId.setId_permiso(99);
        permisoConId.setNombre_permiso("NUEVO_PERMISO");


        Mockito.when(permisoService.crearPermiso(Mockito.any(Permiso.class))).thenReturn(permisoConId);


        mockMvc.perform(post("/permisos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(permisoSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_permiso").value(99))
                .andExpect(jsonPath("$.nombre_permiso").value("NUEVO_PERMISO"));
    }
}
