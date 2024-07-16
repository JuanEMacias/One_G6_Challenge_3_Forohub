package com.aluracursos.forohub.enumerador;

public enum TipoPerfil {
    
    ADMINISTRADOR("Administrador"),
    MODERADOR("Moderador"),
    ESTUDIANTE("Estudiante"),
    INSTRUCTOR("Instructor");
    
    @SuppressWarnings("unused")
    private String nombre;
    
    TipoPerfil(String nombre){
         this.nombre = nombre;
     }
}
