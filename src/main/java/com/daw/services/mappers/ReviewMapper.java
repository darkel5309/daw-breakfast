package com.daw.services.mappers;

import com.daw.persistence.entities.Review;
import com.daw.services.dto.ReviewDTO;

public class ReviewMapper {
	public static ReviewDTO toDTO(Review review) {
		ReviewDTO dto = new ReviewDTO();
		dto.setId(review.getId());
		dto.setFecha(review.getFecha());
		dto.setComentarios(review.getComentarios());
		dto.setImagen(review.getImagen());
		dto.setPuntuacion(review.getPuntuacion());
		dto.setPrecio(review.getPrecio());
		
		dto.setUsuario(review.getUsuario().getUserName());
		dto.setDesayuno(review.getDesayuno().getNombre());
		
		return dto;
	}
	
	public static Review toEntity(ReviewDTO reviewDto) {
		Review entity = new Review();
		entity.setId(reviewDto.getId());
		entity.setFecha(reviewDto.getFecha());
		entity.setComentarios(reviewDto.getComentarios());
		entity.setImagen(reviewDto.getImagen());
		entity.setPuntuacion(reviewDto.getPuntuacion());
		entity.setPrecio(reviewDto.getPrecio());
		
		return entity;
	}

}
