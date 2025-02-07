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
	
	public List<DesayunoDTO> findAll(){
		List<DesayunoDTO> desayunosDTO = new ArrayList<DesayunoDTO>();

		for (Desayuno p : this.desayunoRepository.findAll()) {
			desayunosDTO.add(DesayunoMapper.toDTO(p));
		}

		return desayunosDTO;
	}
	
	public Optional<Desayuno> findById(int idDesayuno){
		return this.desayunoRepository.findById(idDesayuno);
	}
	
	public boolean existsDesayuno(int idDesayuno) {
		return this.desayunoRepository.existsById(idDesayuno);
	}
	
	public Desayuno create(Desayuno desayuno) {
		return this.desayunoRepository.save(desayuno);
	}
	
	public Desayuno save(Desayuno desayuno) {
		return this.desayunoRepository.save(desayuno);
	}
	
	public boolean delete(int idDesayuno) {
		boolean result = false;
		
		if(this.desayunoRepository.existsById(idDesayuno)) {
			this.desayunoRepository.deleteById(idDesayuno);
			
			return true;
		}
		return result;
	}
}
