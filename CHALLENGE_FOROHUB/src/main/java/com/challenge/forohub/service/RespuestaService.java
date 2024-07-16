package com.aluracursos.forohub.service;

import com.aluracursos.forohub.DTO.RegistrodeRespuesta;
import com.aluracursos.forohub.model.Respuesta;
import com.aluracursos.forohub.model.Topic;
import com.aluracursos.forohub.model.User;
import com.aluracursos.forohub.repository.IRespuestaRepository;
import com.aluracursos.forohub.repository.ITopicoRepository;
import com.aluracursos.forohub.repository.IUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {

    @Autowired
    private IRespuestaRepository respuestaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ITopicoRepository topicoRepository;

    @Transactional
    public Respuesta registrarServicio(RegistrodeRespuesta datosRegistroRespuesta) {

        Long topicoId = datosRegistroRespuesta.topicoId();
        Topic topico = topicoRepository.findById(topicoId).orElseThrow(() -> new IllegalArgumentException("Error, no hay resultados para su búsqueda"));

        Long autorId = datosRegistroRespuesta.autorId();
        User autor = usuarioRepository.findById(autorId)
                .orElseThrow(() -> new IllegalArgumentException("Error, no hay resultados para su búsqueda"));

        Respuesta respuesta = new Respuesta(datosRegistroRespuesta, autor, topico);
        return respuestaRepository.save(respuesta);
    }

    public Page<Respuesta> getRespuestasPorTopico(Long topicoId, Pageable pageable) {
        return respuestaRepository.findByTopicoId(topicoId, pageable);
    }

    public void marcarRespuestaComoSolucion(Long idRespuesta) {
        Respuesta respuesta = respuestaRepository.findById(idRespuesta)
                .orElseThrow(() -> new RuntimeException("Error, no hay resultados para su búsqueda"));

        respuesta.darRespuestaComoSolucion();

        respuestaRepository.save(respuesta); 
    }

    public void desmarcarRespuestaComoSolucion(Long idRespuesta) {
       Respuesta respuesta = respuestaRepository.findById(idRespuesta)
                .orElseThrow(() -> new RuntimeException("Error, no hay resultados para su búsqueda"));

        respuesta.desmarcarRespuestaComoSolucion();

        respuestaRepository.save(respuesta); 
    }

}
