package com.aluracursos.forohub.service;

import com.aluracursos.forohub.DTO.RegistrodeTopicos;
import com.aluracursos.forohub.model.Respuesta;
import com.aluracursos.forohub.model.Topic;
import com.aluracursos.forohub.model.User;
import com.aluracursos.forohub.repository.ITopicoRepository;
import com.aluracursos.forohub.repository.IUsuarioRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    @Autowired
    private ITopicoRepository topicoRepository;

    

    @Autowired
    private IUsuarioRepository usuarioRepository;
@Transactional
public Topic registrarTopico(RegistrodeTopicos datosRegistroTopico) {

    Long autorId = datosRegistroTopico.autorId();
    User autor = usuarioRepository.findById(autorId)
            .orElseThrow(() -> new IllegalArgumentException("Error, no hay resultados para su búsqueda"));
    System.out.println(autor);

    
    List<Topic> topicos = topicoRepository.findAll();
    for (Topic topico : topicos) {
        if (topico.getTitulo().equalsIgnoreCase(datosRegistroTopico.titulo())
                && topico.getMensaje().equalsIgnoreCase(datosRegistroTopico.mensaje())) {
            System.out.println("Error de registro");
            return null; 
        }
    }

  
    Topic nuevoTopico = new Topic(datosRegistroTopico, autor);
    Topic topicoGuardado = topicoRepository.save(nuevoTopico);
    System.out.println("Operación exitosa");

    return topicoGuardado;
}


    public Boolean tieneRespuestaComoSolucion(Topic get) {
        Topic topico = topicoRepository.getReferenceById(get.getId());
        Boolean resultado = false;
        for (Respuesta respuesta : topico.getRespuestas()) {
            if (respuesta.getSolucion().equals(Boolean.TRUE)) {
                resultado = true;
            }
        }
        return resultado;
    }

    public Boolean perteneceAlUsuario(Topic topico) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         System.out.println(authentication);
        if (authentication == null || !authentication.isAuthenticated()) {
            return false; 
           
        }

        String nombreUsuarioAutenticado = authentication.getName();
         System.out.println(nombreUsuarioAutenticado);
         System.out.println( topico.getAutor().getUsername().equals(nombreUsuarioAutenticado));
        return topico.getAutor().getUsername().equals(nombreUsuarioAutenticado);
    }

    public Topic obtenerTopicoPorId(Long id) {
      Topic topico = topicoRepository.findById(id).get();
      return topico;
    }
}
