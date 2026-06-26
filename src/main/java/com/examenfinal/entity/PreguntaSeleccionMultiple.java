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
@DiscriminatorValue("SELECCION_MULTIPLE")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PreguntaSeleccionMultiple extends Pregunta {

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> opciones;

    @Convert(converter = StringListConverter.class)
    @Column(name = "respuestas_correctas", columnDefinition = "TEXT")
    private List<String> respuestasCorrectas;

    public PreguntaSeleccionMultiple(String enunciado, Tematica tematica, List<String> opciones, List<String> respuestasCorrectas) {
        super(enunciado, tematica);
        this.opciones = opciones;
        this.respuestasCorrectas = respuestasCorrectas;
    }

    public PreguntaSeleccionMultiple(Long id, String enunciado, Tematica tematica, List<String> opciones, List<String> respuestasCorrectas) {
        super(id, enunciado, tematica);
        this.opciones = opciones;
        this.respuestasCorrectas = respuestasCorrectas;
    }
}