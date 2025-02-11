package com.daw.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Desayuno;

public interface DesayunoRepository extends ListCrudRepository<Desayuno, Integer>{
	
	List<Desayuno> findAllByOrderByPuntuacionDesc();
	List<Desayuno> findByIdEstablecimientoOrderByPuntuacionDesc(int idEstablecimiento);
	List<Desayuno> findByIdEstablecimientoOrderByPrecioAsc(int idEstablecimiento);
	List<Desayuno> findByIdEstablecimiento(int idEstablecimiento);
	Optional<Desayuno> findByNombre(String nombre);
	
}