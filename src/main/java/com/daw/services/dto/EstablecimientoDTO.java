package com.daw.services.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EstablecimientoDTO {
	private Integer id;
	private String nombre;
	private String ubicacion;
	private Double puntuacion;
	private List<DesayunoDTO> desayunos;
}
