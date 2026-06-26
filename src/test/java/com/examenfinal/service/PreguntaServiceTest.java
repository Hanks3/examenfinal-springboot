package com.examenfinal.service;

import com.examenfinal.dto.PreguntaDTO;
import com.examenfinal.entity.Pregunta;
import com.examenfinal.entity.Tematica;
import com.examenfinal.exception.PreguntaNoEncontradaException;
import com.examenfinal.repository.PreguntaRepository;
import com.examenfinal.repository.TematicaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PreguntaServiceTest {

    @Mock
    private PreguntaRepository preguntaRepository;

    @Mock
    private TematicaRepository tematicaRepository;

    @InjectMocks
    private PreguntaServiceImpl preguntaService;

    @Test
    void listarPaginadas_deberiaRetornarPaginaDePreguntas() {
        Tematica tematica = new Tematica(1L, "Matematicas");
        Pregunta pregunta = new Pregunta(1L, "¿Cuanto es 2+2?", tematica);
        Page<Pregunta> pageMock = new PageImpl<>(List.of(pregunta));
        when(preguntaRepository.findAll(any(Pageable.class))).thenReturn(pageMock);

        Page<PreguntaDTO> resultado = preguntaService.listarPaginadas(PageRequest.of(0, 10));

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("¿Cuanto es 2+2?", resultado.getContent().get(0).getEnunciado());
        verify(preguntaRepository).findAll(any(Pageable.class));
    }

    @Test
    void crear_deberiaGuardarPreguntaCorrectamente() {
        Tematica tematica = new Tematica(1L, "Ciencias");
        PreguntaDTO dto = new PreguntaDTO(null, "¿La tierra es redonda?", "V_F", 1L, "Ciencias");
        dto.setOpcionCorrecta(true);
        Pregunta preguntaGuardada = new Pregunta(1L, "¿La tierra es redonda?", tematica);

        when(tematicaRepository.findById(1L)).thenReturn(Optional.of(tematica));
        when(preguntaRepository.save(any(Pregunta.class))).thenReturn(preguntaGuardada);

        PreguntaDTO resultado = preguntaService.crear(dto);

        assertNotNull(resultado);
        assertEquals("¿La tierra es redonda?", resultado.getEnunciado());
        verify(tematicaRepository).findById(1L);
        verify(preguntaRepository).save(any(Pregunta.class));
    }

    @Test
    void obtenerPorId_conIdInexistente_deberiaLanzarExcepcion() {
        when(preguntaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(PreguntaNoEncontradaException.class,
                () -> preguntaService.obtenerPorId(999L));
        verify(preguntaRepository).findById(999L);
    }
}