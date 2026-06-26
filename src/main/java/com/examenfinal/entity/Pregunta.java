package com.examenfinal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pregunta", discriminatorType = DiscriminatorType.STRING)
@Table(name = "preguntas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El enunciado es obligatorio")
    @Size(min = 5, max = 500, message = "El enunciado debe tener entre {min} y {max} caracteres")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    @NotNull(message = "La tematica es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tematica_id")
    private Tematica tematica;

    public Pregunta(String enunciado, Tematica tematica) {
        this.enunciado = enunciado;
        this.tematica = tematica;
    }
}