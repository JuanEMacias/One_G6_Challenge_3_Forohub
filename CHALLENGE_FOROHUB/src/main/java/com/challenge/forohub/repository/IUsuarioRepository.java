package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<User, Long>{

    public UserDetails findByCorreoElectronico(String username);
    
}
