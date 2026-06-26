package com.examenfinal.controller;

import com.examenfinal.dto.PreguntaDTO;
import com.examenfinal.security.CustomUserDetailsService;
import com.examenfinal.security.JwtUtils;
import com.examenfinal.service.PreguntaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PreguntaRestController.class)
@WithMockUser(roles = "ADMIN")
class PreguntaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PreguntaService preguntaService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Test
    void getPreguntas_deberiaRetornarPaginaJson() throws Exception {
        PreguntaDTO dto = new PreguntaDTO(1L, "¿Cuanto es 2+2?", "V_F", 1L, "Matematicas");
        dto.setOpcionCorrecta(true);
        Page<PreguntaDTO> pagina = new PageImpl<>(List.of(dto), PageRequest.of(0, 10), 1);

        when(preguntaService.listarPaginadas(any())).thenReturn(pagina);

        mockMvc.perform(get("/api/preguntas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].enunciado").value("¿Cuanto es 2+2?"))
                .andExpect(jsonPath("$.content[0].tipo").value("V_F"))
                .andExpect(jsonPath("$.content[0].tematicaId").value(1))
                .andExpect(jsonPath("$.content[0].tematicaNombre").value("Matematicas"))
                .andExpect(jsonPath("$.content[0].opcionCorrecta").value(true))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(10));
    }
}