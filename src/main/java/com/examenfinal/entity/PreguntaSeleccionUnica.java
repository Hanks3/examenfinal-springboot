package com.examenfinal.entity;

import com.examenfinal.converter.StringListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@DiscriminatorValue("SELECCION_UNICA")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PreguntaSeleccionUnica extends Pregunta {

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> opciones;

    @Column(name = "respuesta_correcta")
    private String respuestaCorrecta;

    public PreguntaSeleccionUnica(String enunciado, Tematica tematica, List<String> opciones, String respuestaCorrecta) {
        super(enunciado, tematica);
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public PreguntaSeleccionUnica(Long id, String enunciado, Tematica tematica, List<String> opciones, String respuestaCorrecta) {
        super(id, enunciado, tematica);
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
    }
}