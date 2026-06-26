package com.examenfinal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("V_F")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PreguntaVerdaderoFalso extends Pregunta {

    @Column(name = "opcion_correcta")
    private Boolean opcionCorrecta;

    public PreguntaVerdaderoFalso(String enunciado, Tematica tematica, Boolean opcionCorrecta) {
        super(enunciado, tematica);
        this.opcionCorrecta = opcionCorrecta;
    }

    public PreguntaVerdaderoFalso(Long id, String enunciado, Tematica tematica, Boolean opcionCorrecta) {
        super(id, enunciado, tematica);
        this.opcionCorrecta = opcionCorrecta;
    }
}