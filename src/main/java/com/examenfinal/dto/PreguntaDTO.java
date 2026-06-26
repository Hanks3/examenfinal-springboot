package com.examenfinal.dto;

import com.examenfinal.entity.Pregunta;
import com.examenfinal.entity.PreguntaSeleccionMultiple;
import com.examenfinal.entity.PreguntaSeleccionUnica;
import com.examenfinal.entity.PreguntaVerdaderoFalso;
import com.examenfinal.entity.Tematica;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class PreguntaDTO {

    private Long id;

    @NotBlank(message = "El enunciado no puede estar vacio")
    private String enunciado;

    @NotBlank(message = "El tipo no puede estar vacio")
    private String tipo;

    @NotNull(message = "La tematica es obligatoria")
    private Long tematicaId;

    private String tematicaNombre;

    private Boolean opcionCorrecta;

    private List<String> opciones;

    private String respuestaCorrecta;

    private List<String> respuestasCorrectas;

    public PreguntaDTO(Long id, String enunciado, String tipo, Long tematicaId, String tematicaNombre) {
        this.id = id;
        this.enunciado = enunciado;
        this.tipo = tipo;
        this.tematicaId = tematicaId;
        this.tematicaNombre = tematicaNombre;
    }

    public static PreguntaDTO fromEntity(Pregunta pregunta) {
        PreguntaDTO dto = new PreguntaDTO();
        dto.setId(pregunta.getId());
        dto.setEnunciado(pregunta.getEnunciado());
        dto.setTematicaId(pregunta.getTematica().getId());
        dto.setTematicaNombre(pregunta.getTematica().getNombre());

        if (pregunta instanceof PreguntaVerdaderoFalso vf) {
            dto.setTipo("V_F");
            dto.setOpcionCorrecta(vf.getOpcionCorrecta());
        } else if (pregunta instanceof PreguntaSeleccionUnica su) {
            dto.setTipo("SELECCION_UNICA");
            dto.setOpciones(su.getOpciones());
            dto.setRespuestaCorrecta(su.getRespuestaCorrecta());
        } else if (pregunta instanceof PreguntaSeleccionMultiple sm) {
            dto.setTipo("SELECCION_MULTIPLE");
            dto.setOpciones(sm.getOpciones());
            dto.setRespuestasCorrectas(sm.getRespuestasCorrectas());
        }

        return dto;
    }

    public static Pregunta toEntity(PreguntaDTO dto, Tematica tematica) {
        String tipo = dto.getTipo();
        if ("V_F".equals(tipo)) {
            return new PreguntaVerdaderoFalso(dto.getEnunciado(), tematica, dto.getOpcionCorrecta());
        } else if ("SELECCION_UNICA".equals(tipo)) {
            return new PreguntaSeleccionUnica(dto.getEnunciado(), tematica, dto.getOpciones(), dto.getRespuestaCorrecta());
        } else if ("SELECCION_MULTIPLE".equals(tipo)) {
            return new PreguntaSeleccionMultiple(dto.getEnunciado(), tematica, dto.getOpciones(), dto.getRespuestasCorrectas());
        }
        throw new IllegalArgumentException("Tipo de pregunta no valido: " + tipo);
    }
}