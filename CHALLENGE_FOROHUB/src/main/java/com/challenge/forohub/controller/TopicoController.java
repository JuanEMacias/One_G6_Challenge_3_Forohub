package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.DTO.Topics;
import com.aluracursos.forohub.DTO.RegistrodeTopicos;
import com.aluracursos.forohub.DTO.RespuestasData;
import com.aluracursos.forohub.DTO.RespuestaDataTopics;
import com.aluracursos.forohub.model.Topic;
import com.aluracursos.forohub.repository.ITopicoRepository;
import com.aluracursos.forohub.service.TopicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topico")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private ITopicoRepository topicoRepo;

    @Autowired
    private TopicoService topicoService;

    @SuppressWarnings("rawtypes")
    @PostMapping
    public ResponseEntity registrarTopico(@RequestBody @Valid RegistrodeTopicos datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {

        Topic topico = topicoService.registrarTopico(datosRegistroTopico);

        if (topico != null) {
            RegistrodeTopicos datosRespuestaTopico = new RegistrodeTopicos(
                    topico.getTitulo(),
                    topico.getMensaje(),
                    topico.getAutor().getId(),
            );

            URI url = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();

            return ResponseEntity.created(url).body(datosRespuestaTopico);

        } else {

            return ResponseEntity.status(HttpStatus.CONFLICT).body("Opción ya registrada");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<Topics>> listarTopico(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {

        Page<Topic> topicos = topicoRepo.findByStatusTrue(paginacion);
        Page<Topics> datosListadoTopicos = topicos.map(Topics::new);
        return ResponseEntity.ok().body(datosListadoTopicos);
    }

    @SuppressWarnings("rawtypes")
    @PutMapping("/actualizar")
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody @Valid RegistrodeTopicos.DatosActualizarTopico datosActualizarTopico) {
        Optional<Topic> topicoOptional = topicoRepo.findById(datosActualizarTopico.id());

        if (topicoOptional.isPresent()) {
            Topic topico = topicoOptional.get();
            topico.actualizarDatos(datosActualizarTopico);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
             List<RespuestasData> respuestasDTO = topico.getRespuestas().stream().map(respuesta
                    -> new RespuestasData(
                            respuesta.getId(),
                            respuesta.getMensaje(),
                            respuesta.getFechaCreacion().format(formatter),
                            respuesta.getSolucion(),
                            respuesta.getAutor().getNombre(),
                            respuesta.getAutor().getPerfil(),
                            respuesta.getTopico().getTitulo()
                    )
            ).collect(Collectors.toList());
            RespuestaDataTopics datosTopico = new RespuestaDataTopics(
                    topico.getId(),
                    topico.getTitulo(),
                    topico.getMensaje(),
                    topico.getFechaCreacion().format(formatter),
                    topico.getAutor().getNombre(),
                    respuestasDTO
            );
              return ResponseEntity.ok(datosTopico);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error, no hay resultados para su búsqueda");
        }
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topic> topicoOptional = topicoRepo.findById(id);
        if (topicoOptional.isPresent()) {
            Topic topico = topicoOptional.get();
            topicoRepo.deleteById(topico.getId());
            return ResponseEntity.ok().body("Operación exitosa");
        }

        return ResponseEntity.noContent().build();
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/baja/{id}")
    @Transactional
    public ResponseEntity darDeBajaTopico(@PathVariable Long id) {
        Optional<Topic> topicoOptional = topicoRepo.findById(id);
        if (topicoOptional.isPresent()) {
            Topic topico = topicoOptional.get();
            topico.desactivarTopico();
            return ResponseEntity.ok().body("Operación exitosa");
        }
        return ResponseEntity.noContent().build();
    }

    @SuppressWarnings("rawtypes")
    @GetMapping("/alta/{id}")
    @Transactional
    public ResponseEntity darDeAltaTopico(@PathVariable Long id) {
        Optional<Topic> topicoOptional = topicoRepo.findById(id);
        if (topicoOptional.isPresent()) {
            Topic topico = topicoOptional.get();
            topico.activarTopico();
            return ResponseEntity.ok().body("Operación exitosa");
        }
        return ResponseEntity.noContent().build();
    }

    @SuppressWarnings("rawtypes")
    @GetMapping("/detalle/{id}")
    public ResponseEntity retornarDatosTopico(@PathVariable Long id) {
        Optional<Topic> optionalTopico = topicoRepo.findById(id);
        if (optionalTopico.isPresent()) {
            Topic topico = optionalTopico.get();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

             List<RespuestasData> respuestasDTO = topico.getRespuestas().stream().map(respuesta
                    -> new RespuestasData(
                            respuesta.getId(),
                            respuesta.getMensaje(),
                            respuesta.getFechaCreacion().format(formatter),
                            respuesta.getSolucion(),
                            respuesta.getAutor().getNombre(),
                            respuesta.getAutor().getPerfil(),
                            respuesta.getTopico().getTitulo()
                    )
            ).collect(Collectors.toList());

            RespuestaDataTopics datosTopico = new RespuestaDataTopics(
                    topico.getId(),
                    topico.getTitulo(),
                    topico.getMensaje(),
                    topico.getFechaCreacion().format(formatter),
                    topico.getAutor().getNombre(),
                    respuestasDTO
            );
            return ResponseEntity.ok(datosTopico);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún tópico con el ID proporcionado.");
        }
    }

}
