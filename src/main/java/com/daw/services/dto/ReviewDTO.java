package com.daw.services.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {
	private Integer id;
	private Integer idUsuario;
	private Integer idDesayuno;
	private String usuario;
	private String desayuno;
	private LocalDateTime fecha;
	private String comentarios;
	private Integer puntuacion;
	private String imagen;
	private Double precio;


}
