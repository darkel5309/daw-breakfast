package com.daw.services.mappers;

import com.daw.persistence.entities.Usuario;
import com.daw.services.dto.UsuarioDTO;

public class UsuarioMapper {

	public UsuarioDTO toDTO(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO();

		dto.setId(usuario.getId());
		dto.setPassword(usuario.getPassword());

		return dto;
	}
}