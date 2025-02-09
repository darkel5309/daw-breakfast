package com.daw.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	

	public Optional<EstablecimientoDTO> findById(int idEstablecimiento) {
        Optional<Establecimiento> establecimiento = this.establecimientoRepository.findById(idEstablecimiento);
        return establecimiento.map(EstablecimientoMapper::toDTO);
    }
	
	public Establecimiento create(Establecimiento establecimiento) {
		 establecimiento.setPuntuacion(0.0);
        return this.establecimientoRepository.save(establecimiento);
    }
	
	public boolean delete(int idEstablecimiento) {
		boolean result = false;
		
		if(this.establecimientoRepository.existsById(idEstablecimiento)) {
			this.establecimientoRepository.deleteById(idEstablecimiento);
			result = true;
		}
		
		return result;
	}
	public Establecimiento save(Establecimiento establecimiento) {
		return this.establecimientoRepository.save(establecimiento);
	}
	
	public boolean exists(int idEstablecimiento){
		return this.establecimientoRepository.existsById(idEstablecimiento);
	}
	 public List<EstablecimientoDTO> findAllOrderedByPuntuacionDesc() {
	        return this.establecimientoRepository.findAllByOrderByPuntuacionDesc().stream()
	                .map(EstablecimientoMapper::toDTO)
	                .collect(Collectors.toList());
	    }
	 public List<EstablecimientoDTO> findByUbicacion(String ubicacion) {
	        return this.establecimientoRepository.findByUbicacionContainingIgnoreCase(ubicacion).stream()
	                .map(EstablecimientoMapper::toDTO)
	                .collect(Collectors.toList());
	    }
	
	
}
