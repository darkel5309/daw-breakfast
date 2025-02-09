package com.daw.persistence.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Usuario;

import java.util.Optional;


public interface UsuarioRepository extends ListCrudRepository<Usuario, Integer>{
    Optional<Usuario> findByUsername(String username);
}
