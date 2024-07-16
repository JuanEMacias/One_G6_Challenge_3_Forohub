package com.aluracursos.forohub.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrodeTopicos(
        @NotBlank(message = "Error verifique la entrada de texto") String titulo,
        @NotBlank(message = "Error verifique la entrada de texto") String mensaje,
        Long autorId,
        @NotNull Long cursoId
        ) {

    public void setAutorId(Long idAutor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static record DatosActualizarTopico(
            @NotNull Long id,
            String titulo,
            String mensaje,
            Long cursoId){
        
    }
}
