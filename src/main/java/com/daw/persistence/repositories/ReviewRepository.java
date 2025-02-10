package com.daw.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Review;
import com.daw.services.dto.ReviewDTO;

public interface ReviewRepository extends ListCrudRepository<Review, Integer>{
    List<Review> findByIdDesayuno(int idDesayuno);
    List<Review> findByIdUsuario(int idUsuario);
    List<ReviewDTO>findByFechaOrderByAsc();
    List<ReviewDTO>findByFechaOrderByDesc();
    List<ReviewDTO>findByPuntuacionOrderByAsc();
    List<ReviewDTO>findByPuntuacionOrderByDesc();
    List<ReviewDTO>findByFechaOrderByDescByIdDesayuno(int idDesayuno);
    List<ReviewDTO>findByPuntuacionOrderByDescByIdDesayuno(int idDesayuno);
}
