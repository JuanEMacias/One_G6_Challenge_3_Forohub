package com.aluracursos.forohub.service;

import com.aluracursos.forohub.DTO.RegistrodeUsuarios;
import com.aluracursos.forohub.model.User;
import com.aluracursos.forohub.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired 
    private IUsuarioRepository usuarioRepo;
    
    public User registrarUsuario(RegistrodeUsuarios datosRegistroUsuario) {
       
        User usuario = new User(datosRegistroUsuario);
        return usuarioRepo.save(usuario);
    }    

    public User obtenerPorCorreoElectronico(String username) {
       return (User) usuarioRepo.findByCorreoElectronico(username);
    }
}
