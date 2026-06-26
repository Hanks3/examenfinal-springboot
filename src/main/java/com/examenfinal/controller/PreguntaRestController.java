package com.examenfinal.controller;

import com.examenfinal.dto.PreguntaDTO;
import com.examenfinal.service.PreguntaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preguntas")
@RequiredArgsConstructor
public class PreguntaRestController {

    private final PreguntaService preguntaService;

    @GetMapping
    public ResponseEntity<Page<PreguntaDTO>> listarTodas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long tematicaId,
            @RequestParam(required = false) String tipo) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PreguntaDTO> pagina;

        if (tematicaId != null && tipo != null && !tipo.isBlank()) {
            pagina = preguntaService.listarPaginadasPorTematicaYTipo(tematicaId, tipo, pageable);
        } else if (tematicaId != null) {
            pagina = preguntaService.listarPaginadasPorTematica(tematicaId, pageable);
        } else if (tipo != null && !tipo.isBlank()) {
            pagina = preguntaService.listarPaginadasPorTipo(tipo, pageable);
        } else {
            pagina = preguntaService.listarPaginadas(pageable);
        }

        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreguntaDTO> obtenerPorId(@PathVariable Long id) {
        PreguntaDTO dto = preguntaService.obtenerPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PreguntaDTO> crear(@Valid @RequestBody PreguntaDTO preguntaDTO) {
        PreguntaDTO creada = preguntaService.crear(preguntaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreguntaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PreguntaDTO preguntaDTO) {
        PreguntaDTO actualizada = preguntaService.actualizar(id, preguntaDTO);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        preguntaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}