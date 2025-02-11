package com.daw.services;

import com.daw.persistence.entities.Desayuno;
import com.daw.persistence.entities.Review;
import com.daw.persistence.entities.Usuario;
import com.daw.persistence.repositories.ReviewRepository;
import com.daw.services.dto.ReviewDTO;
import com.daw.services.mappers.DesayunoMapper;
import com.daw.services.mappers.ReviewMapper;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private DesayunoService desayunoService;

	@Autowired
	private UsuarioService usuarioService; //

	// Obtener todas las reseñas
	public List<ReviewDTO> findAll() {
		List<ReviewDTO> reviewsDTO = new ArrayList<>();

		for (Review review : this.reviewRepository.findAll()) {
			reviewsDTO.add(ReviewMapper.toDTO(review)); // Mapear a ReviewDTO
		}

		return reviewsDTO;
	}

	// Obtener una reseña por su ID
	public Optional<ReviewDTO> findById(int idReview) {
		Optional<Review> reviewOptional = this.reviewRepository.findById(idReview);

		// Si el Review existe, lo mapeamos a ReviewDTO
		if (reviewOptional.isPresent()) {
			return Optional.of(ReviewMapper.toDTO(reviewOptional.get()));
		} else {
			return Optional.empty();
		}
	}

	// Verificar si la reseña existe por su ID
	public boolean existsReview(int idReview) {
		return this.reviewRepository.existsById(idReview);
	}

	@Transactional
	// Actualizar la puntuacion de desayuno
	private void updateDesayunoPuntuacion(Desayuno desayuno) {
		// Obtener todas las reseñas del desayuno
		List<Review> reviews = this.reviewRepository.findByIdDesayuno(desayuno.getId());

		// Calcular el promedio de las puntuaciones
		double sumaPuntuaciones = 0;
		for (Review review : reviews) {
			sumaPuntuaciones += review.getPuntuacion();
		}

		double puntuacionPromedio = 0;

		if(reviews.size()!=0) {
			puntuacionPromedio= sumaPuntuaciones/reviews.size();
		}
		
		// Actualizar la puntuación promedio del desayuno
		desayuno.setPuntuacion(puntuacionPromedio);

		// Guardar el desayuno con la nueva puntuación
		Desayuno desayunoAct = this.desayunoService.save(desayuno);
		
		this.desayunoService.updateEstablecimientoPuntuacion(desayunoAct.getIdEstablecimiento());
	}

	// Crear una nueva reseña
	public Review createReview(Review review, int idUsuario, int idDesayuno) {
		Optional<Usuario> usuarioOptional = usuarioService.findById(idUsuario);
		if (!usuarioOptional.isPresent()) {
			throw new IllegalArgumentException("Usuario no encontrado.");
		}

		Optional<Desayuno> desayunoOptional = desayunoService.findById(idDesayuno);
		if (!desayunoOptional.isPresent()) {
			throw new IllegalArgumentException("Desayuno no encontrado.");
		}

		// Establecer las entidades relacionadas en la reseña
		review.setUsuario(usuarioOptional.get());
		review.setDesayuno(desayunoOptional.get());
		review.setFecha(java.time.LocalDateTime.now());

		// Guardar la reseña
		review = this.reviewRepository.save(review);

		// Recalcular la puntuación promedio del desayuno
		updateDesayunoPuntuacion(desayunoOptional.get());

		return review; // Devuelve la entidad Review directamente
	}

	// Actualizar una reseña existente
	public Review update(int idReview, Review updatedReview) {
		Optional<Review> review = this.reviewRepository.findById(idReview);

		if (review.isPresent()) {
			Review reviewOP = review.get();

			// Actualizar los valores
			reviewOP.setComentarios(updatedReview.getComentarios());
			reviewOP.setPuntuacion(updatedReview.getPuntuacion());
			reviewOP.setPrecio(updatedReview.getPrecio());
			reviewOP.setImagen(updatedReview.getImagen());

			// Guardamos la reseña actualizada
			reviewOP = this.reviewRepository.save(reviewOP);

			// Recalcular la puntuación promedio del desayuno
			updateDesayunoPuntuacion(reviewOP.getDesayuno());

			return reviewOP; // Convertimos a DTO y lo devolvemos
		} else {
			throw new IllegalArgumentException("Reseña con ID: " + idReview + " no encontrada.");
		}
	}

	// Eliminar una reseña por su ID
	public boolean delete(int idReview) {
	    Optional<Review> review = this.reviewRepository.findById(idReview);

	    if (review.isPresent()) {
	        this.reviewRepository.deleteById(idReview);

	        updateDesayunoPuntuacion(review.get().getDesayuno());
	        return true;
	    } else {
	        throw new IllegalArgumentException("Reseña con ID: " + idReview + " no encontrada.");
	    }
	}

	// Obtener reseñas por desayuno (por ID del desayuno)
	public List<ReviewDTO> findByDesayuno(int idDesayuno) {
		List<ReviewDTO> reviewsDTO = new ArrayList<>();
		List<Review> reviews = this.reviewRepository.findByIdDesayuno(idDesayuno);

		for (Review review : reviews) {
			reviewsDTO.add(ReviewMapper.toDTO(review)); // Mapear a DTO
		}

		return reviewsDTO;
	}

	// Obtener reseñas por usuario (por ID del usuario)
	public List<ReviewDTO> findByUsuario(int idUsuario) {
		List<ReviewDTO> reviewsDTO = new ArrayList<>();
		List<Review> reviews = this.reviewRepository.findByIdUsuario(idUsuario);

		for (Review review : reviews) {
			reviewsDTO.add(ReviewMapper.toDTO(review)); // Mapear a DTO
		}

		return reviewsDTO;
	}
	
	//Obtener review ordenadas por fecha(DESC)
	

    public List<ReviewDTO> findAllByOrderByFechaAsc() {
    	return this.reviewRepository.findAllByOrderByFechaAsc().stream()
				.map(ReviewMapper::toDTO)
				.collect(Collectors.toList());
        
    }

   public List<ReviewDTO> findAllByOrderByFechaDesc() {
        return reviewRepository.findAllByOrderByFechaDesc().stream().map(ReviewMapper::toDTO)
				.collect(Collectors.toList());
    }

    public List<ReviewDTO> findAllByOrderByPuntuacionAsc() {
        return reviewRepository.findAllByOrderByPuntuacionAsc().stream().map(ReviewMapper::toDTO)
				.collect(Collectors.toList());
    }

    public List<ReviewDTO> findAllByOrderByPuntuacionDesc() {
        return reviewRepository.findAllByOrderByPuntuacionDesc().stream().map(ReviewMapper::toDTO)
				.collect(Collectors.toList());
    }

    public List<ReviewDTO> findByIdDesayunoOrderByFechaDesc(int idDesayuno) {
        return reviewRepository.findByIdDesayunoOrderByFechaDesc(idDesayuno).stream().map(ReviewMapper::toDTO)
				.collect(Collectors.toList());
    }

    public List<ReviewDTO> findByIdDesayunoOrderByPuntuacionDesc(int idDesayuno) {
        return reviewRepository.findByIdDesayunoOrderByPuntuacionDesc(idDesayuno).stream().map(ReviewMapper::toDTO)
				.collect(Collectors.toList());
    }
}