package com.examenfinal.service;

import com.examenfinal.dto.PreguntaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PreguntaService {

    Page<PreguntaDTO> listarPaginadas(Pageable pageable);

    Page<PreguntaDTO> listarPaginadasPorTematica(Long tematicaId, Pageable pageable);

    Page<PreguntaDTO> listarPaginadasPorTipo(String tipo, Pageable pageable);

    Page<PreguntaDTO> listarPaginadasPorTematicaYTipo(Long tematicaId, String tipo, Pageable pageable);

    PreguntaDTO obtenerPorId(Long id);

    PreguntaDTO crear(PreguntaDTO preguntaDTO);

    PreguntaDTO actualizar(Long id, PreguntaDTO preguntaDTO);

    void eliminar(Long id);
}
