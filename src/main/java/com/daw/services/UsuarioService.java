package com.daw.services;

import com.daw.persistence.entities.Usuario;
import com.daw.persistence.repositories.UsuarioRepository;
import com.daw.services.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Devuelve true si existe usuario y viceversa
    public boolean existsUser(int idUsuario){
        return this.usuarioRepository.existsById(idUsuario);
    }

    //Encontrar todos los usuarios
    public List<Usuario> findAll(){
        return this.usuarioRepository.findAll();
    }

    //Encontrar un usuario por id
    public Optional<Usuario> findById(int idUsuario){
        return this.usuarioRepository.findById(idUsuario);
    }

    //Crear un usuario
    public Usuario create(Usuario usuario){
        return this.usuarioRepository.save(usuario);
    }

    //Actualizar un usuario
    public Usuario update(Usuario usuario){
        Optional<Usuario> usuarioOptional = this.usuarioRepository.findById(usuario.getId());
        if(usuarioOptional.isPresent()){
          Usuario user = usuarioOptional.get();
            return this.usuarioRepository.save(user);
        }else{
            throw new IllegalArgumentException("El usuario con ID:"+ usuario.getId() + "no se encuentra registrado");
        }
    }

    //Borrar un usuario
    public boolean delete(int idUsuario) {
        boolean result = false;

        if (this.usuarioRepository.existsById(idUsuario)) {
            this.usuarioRepository.deleteById(idUsuario);

            return true;
        }
        return result;
    }

    //Modificar la contraseña del usuario
    //Lo que he hecho ha sido crearle un DTO para que solo pasandole el id y la contraseña
    //me modifica la contraseña, asi que una vez modificada me devolverá el usuario entero
    //con la contraseña cambiada
    public Usuario updatePassw(UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOptional = this.usuarioRepository.findById(usuarioDTO.getId());

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            usuario.setPassword(usuarioDTO.getPassword());

            return this.usuarioRepository.save(usuario);
        } else {
            throw new IllegalArgumentException("El usuario con ID: " + usuarioDTO.getId() + " no se encuentra registrado.");
        }
    }



}
