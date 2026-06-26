package com.examenfinal.repository;

import com.examenfinal.entity.Pregunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {

    Page<Pregunta> findByTematicaId(Long tematicaId, Pageable pageable);

    @Query(value = "SELECT * FROM preguntas WHERE tipo_pregunta = :tipo",
           countQuery = "SELECT COUNT(*) FROM preguntas WHERE tipo_pregunta = :tipo",
           nativeQuery = true)
    Page<Pregunta> findByTipoPregunta(@Param("tipo") String tipo, Pageable pageable);

    @Query(value = "SELECT * FROM preguntas WHERE tematica_id = :tematicaId AND tipo_pregunta = :tipo",
           countQuery = "SELECT COUNT(*) FROM preguntas WHERE tematica_id = :tematicaId AND tipo_pregunta = :tipo",
           nativeQuery = true)
    Page<Pregunta> findByTematicaIdAndTipoPregunta(@Param("tematicaId") Long tematicaId,
                                                   @Param("tipo") String tipo,
                                                   Pageable pageable);
}
