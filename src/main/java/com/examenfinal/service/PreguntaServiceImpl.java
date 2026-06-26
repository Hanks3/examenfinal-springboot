package com.examenfinal.service;

import com.examenfinal.dto.PreguntaDTO;
import com.examenfinal.entity.Pregunta;
import com.examenfinal.entity.Tematica;
import com.examenfinal.exception.PreguntaNoEncontradaException;
import com.examenfinal.repository.PreguntaRepository;
import com.examenfinal.repository.TematicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PreguntaServiceImpl implements PreguntaService {

    private final PreguntaRepository preguntaRepository;
    private final TematicaRepository tematicaRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<PreguntaDTO> listarPaginadas(Pageable pageable) {
        return preguntaRepository.findAll(pageable)
                .map(PreguntaDTO::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PreguntaDTO> listarPaginadasPorTematica(Long tematicaId, Pageable pageable) {
        return preguntaRepository.findByTematicaId(tematicaId, pageable)
                .map(PreguntaDTO::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PreguntaDTO> listarPaginadasPorTipo(String tipo, Pageable pageable) {
        return preguntaRepository.findByTipoPregunta(tipo, pageable)
                .map(PreguntaDTO::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PreguntaDTO> listarPaginadasPorTematicaYTipo(Long tematicaId, String tipo, Pageable pageable) {
        return preguntaRepository.findByTematicaIdAndTipoPregunta(tematicaId, tipo, pageable)
                .map(PreguntaDTO::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public PreguntaDTO obtenerPorId(Long id) {
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new PreguntaNoEncontradaException(id));
        return PreguntaDTO.fromEntity(pregunta);
    }

    @Override
    public PreguntaDTO crear(PreguntaDTO dto) {
        Tematica tematica = tematicaRepository.findById(dto.getTematicaId())
                .orElseThrow(() -> new RuntimeException("Tematica no encontrada con id: " + dto.getTematicaId()));
        Pregunta pregunta = PreguntaDTO.toEntity(dto, tematica);
        pregunta = preguntaRepository.save(pregunta);
        return PreguntaDTO.fromEntity(pregunta);
    }

    @Override
    public PreguntaDTO actualizar(Long id, PreguntaDTO dto) {
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new PreguntaNoEncontradaException(id));
        Tematica tematica = tematicaRepository.findById(dto.getTematicaId())
                .orElseThrow(() -> new RuntimeException("Tematica no encontrada con id: " + dto.getTematicaId()));
        pregunta.setEnunciado(dto.getEnunciado());
        pregunta.setTematica(tematica);
        pregunta = preguntaRepository.save(pregunta);
        return PreguntaDTO.fromEntity(pregunta);
    }

    @Override
    public void eliminar(Long id) {
        if (!preguntaRepository.existsById(id)) {
            throw new PreguntaNoEncontradaException(id);
        }
        preguntaRepository.deleteById(id);
    }
}