package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.model.Topic;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITopicoRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findByStatusTrue(Pageable paginacion);

    
    @Query(value = "SELECT * FROM topicos t JOIN respuestas r ON t.id = r.topico_id WHERE r.id = ?1", nativeQuery = true)
    Optional<Topic> findByRespuestaId(Long respuestaId);

}
