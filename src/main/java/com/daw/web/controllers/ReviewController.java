package com.daw.web.controllers;

import com.daw.persistence.entities.Establecimiento;
import com.daw.persistence.entities.Review;
import com.daw.services.ReviewService;
import com.daw.services.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")

public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Obtener todas las reseñas
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.findAll();
        return ResponseEntity.ok(reviews);
    }

    // Obtener una reseña por su ID
    @GetMapping("/{idReview}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable int idReview) {
        Optional<ReviewDTO> review = reviewService.findById(idReview);
        if (review.isPresent()) {
            return ResponseEntity.ok(review.get());
        } else {
            return ResponseEntity.notFound().build(); // Si no se encuentra la reseña
        }
    }

    // Crear una nueva reseña
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review, @RequestParam int idUsuario, @RequestParam int idDesayuno) {
        Review savedReview = reviewService.createReview(review, idUsuario, idDesayuno);
        return ResponseEntity.ok(savedReview);
    }

    // Actualizar una reseña existente
    @PutMapping("/{idReview}")
    public ResponseEntity<Review> updateReview(@PathVariable int idReview, @RequestBody Review Review) {
        if (reviewService.existsReview(idReview)) {
            Review updatedReview = reviewService.update(idReview, Review);
            return ResponseEntity.ok(updatedReview);
        } else {
            return ResponseEntity.notFound().build(); // Reseña no encontrada
        }
    }

    // Eliminar una reseña por su ID
    @DeleteMapping("/{idReview}")
	public ResponseEntity<Review> delete(@PathVariable int idReview) {
		if(this.reviewService.delete(idReview)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

    // Obtener reseñas por desayuno (por ID del desayuno)
    @GetMapping("/desayuno/{idDesayuno}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByDesayuno(@PathVariable int idDesayuno) {
        List<ReviewDTO> reviews = reviewService.findByDesayuno(idDesayuno);
        return ResponseEntity.ok(reviews);
    }

    // Obtener reseñas por usuario (por ID del usuario)
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUsuario(@PathVariable int idUsuario) {
        List<ReviewDTO> reviews = reviewService.findByUsuario(idUsuario);
        return ResponseEntity.ok(reviews);
    }
    
    //arreglar
    // Obtener reviews ordenadas por fecha (DESC)
    @GetMapping("/fecha/desc")
    public ResponseEntity<List<ReviewDTO>> getFechasDesc() {
        List<ReviewDTO> reviews = reviewService.getFechasDesc();
        return ResponseEntity.ok(reviews);
    }

    // Obtener reviews ordenadas por fecha (ASC)
    @GetMapping("/fecha/asc")
    public ResponseEntity<List<ReviewDTO>> getFechasAsc() {
        List<ReviewDTO> reviews = reviewService.getFechasAsc();
        return ResponseEntity.ok(reviews);
    }

    // Obtener todas las reviews ordenadas por puntuación (DESC)
    @GetMapping("/puntuacion/desc")
    public ResponseEntity<List<ReviewDTO>> getPuntuacionDesc() {
        List<ReviewDTO> reviews = reviewService.getPuntuacionDesc();
        return ResponseEntity.ok(reviews);
    }
    
 // Obtener todas las reviews ordenadas por puntuación (ASC)
    @GetMapping("/puntuacion/asc")
    public ResponseEntity<List<ReviewDTO>> getPuntuacionAsc() {
        List<ReviewDTO> reviews = reviewService.getPuntuacionAsc();
        return ResponseEntity.ok(reviews);
    }

    // Obtener reviews ordenadas por fecha (DESC) de un desayuno
    @GetMapping("/desayuno/{idDesayuno}/fecha/desc")
    public ResponseEntity<List<ReviewDTO>> getFechaDescDesay(@PathVariable int idDesayuno) {
        List<ReviewDTO> reviews = reviewService.getFechaDescDesay(idDesayuno);
        return ResponseEntity.ok(reviews);
    }

    // Obtener todas las reviews ordenadas por puntuación (DESC) de un desayuno
    @GetMapping("/desayuno/{idDesayuno}/puntuacion/desc")
    public ResponseEntity<List<ReviewDTO>> getPuntuacionDescDesay(@PathVariable int idDesayuno) {
        List<ReviewDTO> reviews = reviewService.getPuntuacionDescDesay(idDesayuno);
        return ResponseEntity.ok(reviews);
    }
}


