package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.DTO.RegistrodeUsuarios;
import com.aluracursos.forohub.DTO.RespuestaDataUser;
import com.aluracursos.forohub.model.User;
import com.aluracursos.forohub.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuario")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/registrar")
    public ResponseEntity registrarUsuario(@ModelAttribute RegistrodeUsuarios datosRegistroUsuario,
                                           UriComponentsBuilder uriComponentsBuilder) {

        User usuario = usuarioService.registrarUsuario(datosRegistroUsuario);

        RespuestaDataUser datosRespuestaUsuario = new RespuestaDataUser(
                usuario.getNombre(),
                usuario.getCorreoElectronico(),
                usuario.getPerfil());

        URI url = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaUsuario);
    }
}
