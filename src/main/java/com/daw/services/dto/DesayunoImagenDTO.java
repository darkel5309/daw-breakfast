package com.daw.services.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DesayunoImagenDTO {
	private String imagen;

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
}