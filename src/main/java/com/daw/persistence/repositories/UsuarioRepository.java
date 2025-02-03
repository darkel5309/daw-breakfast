package com.daw.persistence.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Usuario;


public interface UsuarioRepository extends ListCrudRepository<Usuario, Integer>{

}
