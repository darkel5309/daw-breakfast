package com.daw.web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.daw.services.EstablecimientoService;
import com.daw.services.dto.EstablecimientoDTO;


@RequestMapping("/establecimientos")
@Controller
public class EstablecimientoController {

	@Autowired
	private EstablecimientoService establecimientoService;
	@GetMapping
	public ResponseEntity<List<EstablecimientoDTO>> list() {
		return ResponseEntity.ok(this.establecimientoService.findAll());
	}
	
	@GetMapping("/{idEstablecimiento}")
	public ResponseEntity<EstablecimientoDTO> findById(@PathVariable int idEstablecimiento) {
		Optional<EstablecimientoDTO> establecimiento = this.establecimientoService.findById(idEstablecimiento);
		if(establecimiento.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(establecimiento.get());
	}
}
