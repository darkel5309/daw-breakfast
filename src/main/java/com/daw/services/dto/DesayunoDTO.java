package com.daw.services.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DesayunoDTO {
	private Integer id;
	private String establecimiento;
	private String nombre;
	private Double precio;
	private String imagen;
	private Double puntuacion;
	private List<ReviewDTO> reviews;
	

}
