package com.daw.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Review;
import com.daw.services.dto.ReviewDTO;

public interface ReviewRepository extends ListCrudRepository<Review, Integer>{
    List<Review> findByIdDesayuno(int idDesayuno);
    List<Review> findByIdUsuario(int idUsuario);
    List<ReviewDTO> findAllByOrderByFechaAsc();
    List<ReviewDTO> findAllByOrderByFechaDesc();
    List<ReviewDTO> findAllByOrderByPuntuacionAsc();
    List<ReviewDTO> findAllByOrderByPuntuacionDesc();
    List<ReviewDTO> findByIdDesayunoOrderByFechaDesc(int idDesayuno);
    List<ReviewDTO> findByIdDesayunoOrderByPuntuacionDesc(int idDesayuno);
}
