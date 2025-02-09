package com.daw.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Establecimiento;


public interface EstablecimientoRepository extends ListCrudRepository<Establecimiento, Integer>{
	    List<Establecimiento> findAllByOrderByPuntuacionDesc();
	    List<Establecimiento> findByUbicacionContainingIgnoreCase(String ubicacion);
}
