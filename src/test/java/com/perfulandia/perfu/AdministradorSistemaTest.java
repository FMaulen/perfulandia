package com.perfulandia.perfu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfu.Model.AdministradorSistema;
import com.perfulandia.perfu.Repository.AdministradorSistemaRepository;
import com.perfulandia.perfu.Services.AdministradorSistemaService;
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
public class AdministradorSistemaTest {

    @Autowired
    private AdministradorSistemaRepository administradorSistemaRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdministradorSistemaService administradorSistemaService;


    @Test
    void findAllAdminsRepositoryTest() {
        List<AdministradorSistema> admins = administradorSistemaRepository.findAll();
        assertNotNull(admins);
        assertEquals(1, admins.size());
    }


    @Test
    void checkAdminNameRepositoryTest() {
        AdministradorSistema admin = administradorSistemaRepository.findById(1).get();
        assertNotNull(admin);
        assertEquals("Admin de Prueba", admin.getNombre());
    }


    @Test
    void getAllAdminsControllerTest() throws Exception {

        AdministradorSistema admin = new AdministradorSistema();
        admin.setId_administrador_sistema(10);
        admin.setNombre("Admin Mock");
        List<AdministradorSistema> listaMock = Collections.singletonList(admin);


        Mockito.when(administradorSistemaService.listarAdmins()).thenReturn(listaMock);


        mockMvc.perform(get("/admin-sistema"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.administradorSistemaList[0].nombre").value("Admin Mock"));
    }


    @Test
    void createAdminControllerTest() throws Exception {

        AdministradorSistema adminSinId = new AdministradorSistema();
        adminSinId.setNombre("Nuevo Admin");
        adminSinId.setCorreo("nuevo@perfulandia.com");

        AdministradorSistema adminConId = new AdministradorSistema();
        adminConId.setId_administrador_sistema(5);
        adminConId.setNombre("Nuevo Admin");
        adminConId.setCorreo("nuevo@perfulandia.com");


        Mockito.when(administradorSistemaService.registrarAdmin(Mockito.any(AdministradorSistema.class))).thenReturn(adminConId);


        mockMvc.perform(post("/admin-sistema")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(adminSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_administrador_sistema").value(5))
                .andExpect(jsonPath("$.nombre").value("Nuevo Admin"));
    }
}
