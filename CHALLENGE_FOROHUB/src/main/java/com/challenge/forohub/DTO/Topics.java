package com.aluracursos.forohub.DTO;

import com.aluracursos.forohub.model.Topic;
import com.aluracursos.forohub.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record Topics(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        LocalDateTime fechaCreacion,
        @NotNull
        Boolean status,
        @NotNull
        Usuario autor,
        @NotNull
        ) 

        {

            public Topics(Topic topico) {
                this(topico.getTitulo(),
                    topico.getMensaje(),
                topico.getFechaCreacion(),
                     topico.getStatus(),
                      topico.getAutor(),
                      );
            }
        }

