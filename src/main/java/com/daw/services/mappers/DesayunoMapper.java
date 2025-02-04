package com.daw.services.mappers;

import java.util.ArrayList;
import java.util.List;

import com.daw.persistence.entities.Desayuno;
import com.daw.persistence.entities.Review;
import com.daw.services.dto.DesayunoDTO;
import com.daw.services.dto.ReviewDTO;

public class DesayunoMapper {
	
	public static DesayunoDTO toDTO(Desayuno desayuno) {
		DesayunoDTO dto = new DesayunoDTO();
		dto.setId(desayuno.getId());
		dto.setNombre(desayuno.getNombre());
		dto.setPrecio(desayuno.getPrecio());
		dto.setImagen(desayuno.getImagen());
		dto.setPuntuacion(desayuno.getPuntuacion());
		
		dto.setEstablecimiento(desayuno.getEstablecimiento().getNombre());
		
		List<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
		for(Review r : desayuno.getReviews()) {
			reviews.add(ReviewMapper.toDTO(r));
		}
		return dto;
	}
	

}
