package com.aluracursos.forohub.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public record RespuestaDataTopics(
        @NotNull Long id,
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull String fechaCreacion,
        @NotNull String autor,
        @NotNull List<RespuestasData> respuestas
) {}