package com.daw.web.controllers;

import com.daw.persistence.entities.Desayuno;
import com.daw.persistence.entities.Usuario;
import com.daw.services.UsuarioService;
import com.daw.services.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")

public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	// Encontrar todos los usuarios
	@GetMapping
	public ResponseEntity<List<Usuario>> getAllUsuarios() {
		return ResponseEntity.ok(this.usuarioService.findAll());
	}

	// Encontrar usuario por id
	@GetMapping("/{idUsuario}")
	public ResponseEntity<Usuario> getByIdUSuario(@PathVariable int idUsuario) {
		Optional<Usuario> usuario = this.usuarioService.findById(idUsuario);

		if (this.usuarioService.existsUser(idUsuario)) {
			return ResponseEntity.ok(usuario.get());
		}
		return ResponseEntity.notFound().build();
	}

	// Crear contraseña
	@PostMapping
	public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
		Usuario savedUsuario = this.usuarioService.create(usuario);
		return new ResponseEntity<>(savedUsuario, HttpStatus.CREATED);
	}

	// Actualizar un usuario
	@PutMapping("/{idUsuario}")
	public ResponseEntity<Usuario> update(@PathVariable int idUsuario, @RequestBody Usuario usuario) {
		if (idUsuario != usuario.getId()) {
			return ResponseEntity.badRequest().build();
		} else if (!this.usuarioService.existsUser(idUsuario)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.usuarioService.save(usuario));
	}

	// Actualizar contraseña
	@PutMapping("/actualizarC/{idUsuario}")
	public ResponseEntity<Usuario> updatePasswUsuario(@PathVariable int idUsuario, @RequestBody UsuarioDTO usuarioDTO) {
		if (idUsuario != usuarioDTO.getId()) {
			return ResponseEntity.badRequest().build();
		}
		if (!this.usuarioService.existsUser(idUsuario)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(this.usuarioService.updatePassw(usuarioDTO));
	}

	// Borrar usuario
	@DeleteMapping("/{idUsuario}")
	public ResponseEntity<Desayuno> delete(@PathVariable int idUsuario) {
		if (this.usuarioService.delete(idUsuario)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}