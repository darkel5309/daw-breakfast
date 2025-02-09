package com.daw.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Desayuno;
import com.daw.persistence.repositories.DesayunoRepository;
import com.daw.services.dto.DesayunoDTO;
import com.daw.services.mappers.DesayunoMapper;

@Service
public class DesayunoService {

	@Autowired
	private DesayunoRepository desayunoRepository;

	public List<DesayunoDTO> findAll() {
		List<DesayunoDTO> desayunosDTO = new ArrayList<DesayunoDTO>();

		for (Desayuno p : this.desayunoRepository.findAll()) {
			desayunosDTO.add(DesayunoMapper.toDTO(p));
		}

		return desayunosDTO;
	}

	public Optional<Desayuno> findById(int idDesayuno) {
		return this.desayunoRepository.findById(idDesayuno);
	}

	public boolean existsDesayuno(int idDesayuno) {
		return this.desayunoRepository.existsById(idDesayuno);
	}

	public Desayuno create(Desayuno desayuno) {
		desayuno.setPuntuacion(0.0);
		if (desayuno.getImagen() == null) {
			desayuno.setImagen("https://i.pinimg.com/736x/6d/7a/43/6d7a43e03c4a75a218a47bb6fd5bfcd0.jpg");
		}
		return this.desayunoRepository.save(desayuno);	
	}

	public Desayuno save(Desayuno desayuno) {
		return this.desayunoRepository.save(desayuno);
	}

	public boolean delete(int idDesayuno) {
		boolean result = false;

		if (this.desayunoRepository.existsById(idDesayuno)) {
			this.desayunoRepository.deleteById(idDesayuno);

			result = true;
		}
		return result;
	}

	public List<Desayuno> getByPuntuacion() {
		return this.desayunoRepository.findAllByOrderByPuntuacionDesc();
	}

	public List<Desayuno> getByPuntuacionFromEstablecimiento(int idEstablecimiento) {
		return this.desayunoRepository.findByIdEstablecimientoOrderByPuntuacionDesc(idEstablecimiento);
	}

	public List<Desayuno> getByPrecioFromEstablecimiento(int idEstablecimiento) {
		return this.desayunoRepository.findByIdEstablecimientoOrderByPrecioAsc(idEstablecimiento);
	}

	public List<Desayuno> getAllFromEstablecimiento(int idEstablecimiento) {
		return this.desayunoRepository.findByIdEstablecimiento(idEstablecimiento);
	}

	public Desayuno modImagen(int idDesayuno, String imagen) {
		Desayuno desayuno = this.desayunoRepository.findById(idDesayuno).get();

		desayuno.setImagen(imagen);

		Desayuno desActualizado = this.desayunoRepository.save(desayuno);

		return desActualizado;
	}
}
