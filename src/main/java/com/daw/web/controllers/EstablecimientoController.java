package com.daw.web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.daw.persistence.entities.Establecimiento;
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
	
	@PostMapping
    public ResponseEntity<Establecimiento> create(@RequestBody Establecimiento establecimiento) {
        Establecimiento savedEstablecimiento = this.establecimientoService.create(establecimiento);
        return new ResponseEntity<>(savedEstablecimiento, HttpStatus.CREATED);
    }
	
	@DeleteMapping("/{idEstablecimiento}")
	public ResponseEntity<Establecimiento> delete(@PathVariable int idEstablecimiento) {
		if(this.establecimientoService.delete(idEstablecimiento)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
	@PutMapping("/{idEstablecimiento}")
	public ResponseEntity<Establecimiento> update(@PathVariable int idEstablecimiento, @RequestBody Establecimiento establecimiento) {
		if(idEstablecimiento != establecimiento.getId()) {
			return ResponseEntity.badRequest().build();
		}
		else if(!this.establecimientoService.exists(idEstablecimiento)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.establecimientoService.save(establecimiento));
	}
	@GetMapping("/rating")
    public ResponseEntity<List<EstablecimientoDTO>> listByPuntuacionDesc() {
        return ResponseEntity.ok(this.establecimientoService.findAllOrderedByPuntuacionDesc());
    }
	@GetMapping("/buscar")
    public ResponseEntity<List<EstablecimientoDTO>> findByUbicacion(@RequestParam String ubicacion) {
        return ResponseEntity.ok(this.establecimientoService.findByUbicacion(ubicacion));
    }
	
}
