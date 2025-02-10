package com.daw.services;

import com.daw.persistence.entities.Desayuno;
import com.daw.persistence.entities.Review;
import com.daw.persistence.entities.Usuario;
import com.daw.persistence.repositories.ReviewRepository;
import com.daw.services.dto.ReviewDTO;
import com.daw.services.mappers.ReviewMapper;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

		double puntuacionPromedio = sumaPuntuaciones / reviews.size();

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
	public ReviewDTO update(int idReview, ReviewDTO updatedReviewDTO) {
		Optional<Review> review = this.reviewRepository.findById(idReview);

		if (review.isPresent()) {
			Review reviewOP = review.get();

			// Actualizar los valores
			reviewOP.setComentarios(updatedReviewDTO.getComentarios());
			reviewOP.setPuntuacion(updatedReviewDTO.getPuntuacion());
			reviewOP.setPrecio(updatedReviewDTO.getPrecio());
			reviewOP.setImagen(updatedReviewDTO.getImagen());

			// Guardamos la reseña actualizada
			reviewOP = this.reviewRepository.save(reviewOP);

			// Recalcular la puntuación promedio del desayuno
			updateDesayunoPuntuacion(reviewOP.getDesayuno());

			return ReviewMapper.toDTO(reviewOP); // Convertimos a DTO y lo devolvemos
		} else {
			throw new IllegalArgumentException("Reseña con ID: " + idReview + " no encontrada.");
		}
	}

	// Eliminar una reseña por su ID
	public boolean delete(int idReview) {
		Optional<Review> review = this.reviewRepository.findById(idReview);

		if (review.isPresent()) {
			this.reviewRepository.deleteById(idReview);

			// Recalcular la puntuación promedio del desayuno
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
	public List<ReviewDTO> getFechasDesc(){
		return this.reviewRepository.findAllByOrderByFechaDesc();
	}
	
	//Obtener review ordenados por fecha(ASC)
	public List<ReviewDTO> getFechasAsc(){
		return this.reviewRepository.findAllByOrderByFechaAsc();
	}
	
	//Obtener todas las revies ordenadas por puntuacion(DESC)
	public List<ReviewDTO> getPuntuacionDesc(){
		return this.reviewRepository.findAllByOrderByPuntuacionDesc();
	}
	
	//Obtener todas las revies ordenadas por puntuacion(ASC)
		public List<ReviewDTO> getPuntuacionAsc(){
			return this.reviewRepository.findAllByOrderByPuntuacionAsc();
		}
		
	//Obtener review ordenados por fecha(ASC) de un desayuno
	public List<ReviewDTO> getFechaDescDesay(int idDesayuno){
		return this.reviewRepository.findByIdDesayunoOrderByFechaDesc(idDesayuno);
	}
	//Obtener todas las revies ordenadas por puntuacion(DESC) de un desayuno
	public List<ReviewDTO> getPuntuacionDescDesay(int idDesayuno){
		return this.reviewRepository.findByIdDesayunoOrderByPuntuacionDesc(idDesayuno);
	}
}