package com.daw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daw.persistence.entities.Establecimiento;
import com.daw.persistence.repositories.EstablecimientoRepository;
import com.daw.services.dto.EstablecimientoDTO;
import com.daw.services.mappers.EstablecimientoMapper;




@Service
public class EstablecimientoService {

	@Autowired
	private EstablecimientoRepository establecimientoRepository;
	
	public List<EstablecimientoDTO> findAll(){
		List<EstablecimientoDTO> establecimientoDTO = new ArrayList<EstablecimientoDTO>();
		
		for(Establecimiento e : this.establecimientoRepository.findAll()) {
			establecimientoDTO.add(EstablecimientoMapper.toDTO(e));
		}

		return establecimientoDTO;
	}
	
	
	
	
	
}
