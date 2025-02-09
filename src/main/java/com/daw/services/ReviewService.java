package com.daw.services;

import com.daw.persistence.entities.Desayuno;
import com.daw.persistence.entities.Review;
import com.daw.persistence.entities.Usuario;
import com.daw.persistence.repositories.ReviewRepository;
import com.daw.services.dto.ReviewDTO;
import com.daw.services.mappers.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    //Actualizar la puntuacion de desayuno
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
        this.desayunoService.save(desayuno);
    }


    // Crear una nueva reseña
    public ReviewDTO create(ReviewDTO reviewDTO) {
        Optional<Usuario> usuarioOptional = usuarioService.findByUsername(reviewDTO.getUsuario());
        if (!usuarioOptional.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }

        Optional<Desayuno> desayunoOptional = desayunoService.obtenerNombreDesayuno(reviewDTO.getDesayuno());
        if (!desayunoOptional.isPresent()) {
            throw new IllegalArgumentException("Desayuno no encontrado.");
        }

        // Crear una nueva reseña usando los datos del DTO
        Review review = new Review();
        review.setUsuario(usuarioOptional.get());
        review.setDesayuno(desayunoOptional.get());
        review.setComentarios(reviewDTO.getComentarios());
        review.setPuntuacion(reviewDTO.getPuntuacion());
        review.setImagen(reviewDTO.getImagen());
        review.setPrecio(reviewDTO.getPrecio());
        review.setFecha(java.time.LocalDateTime.now());

        // Guardamos la reseña
        review = this.reviewRepository.save(review);

        // Recalcular la puntuación promedio del desayuno
        updateDesayunoPuntuacion(desayunoOptional.get());

        // Convertimos la entidad a ReviewDTO usando el ReviewMapper
        return ReviewMapper.toDTO(review); // Aquí usamos el mapper para convertir la entidad a DTO
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
}