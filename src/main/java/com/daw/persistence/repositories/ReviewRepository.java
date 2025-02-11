package com.daw.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Review;

public interface ReviewRepository extends ListCrudRepository<Review, Integer> {

	List<Review> findByIdDesayuno(int idDesayuno);
	List<Review> findByIdUsuario(int idUsuario);
	List<Review> findAllByOrderByFechaAsc();
	List<Review> findAllByOrderByFechaDesc();
	List<Review> findAllByOrderByPuntuacionAsc();
	List<Review> findAllByOrderByPuntuacionDesc();
	List<Review> findByIdDesayunoOrderByFechaDesc(int idDesayuno);
	List<Review> findByIdDesayunoOrderByPuntuacionDesc(int idDesayuno);

}