package com.daw.persistence.repositories;

import com.daw.persistence.entities.Usuario;
import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Review;

import java.util.List;

public interface ReviewRepository extends ListCrudRepository<Review, Integer>{
    List<Review> findByIdDesayuno(int idDesayuno);
    List<Review> findByIdUsuario(int idUsuario);
}
