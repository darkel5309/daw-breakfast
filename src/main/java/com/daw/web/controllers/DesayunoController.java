package com.daw.web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Desayuno;
import com.daw.services.DesayunoService;
import com.daw.services.dto.DesayunoDTO;

@RestController
@RequestMapping("/desayunos")
public class DesayunoController {

	@Autowired
	private DesayunoService desayunoService;

	@GetMapping
	public ResponseEntity<List<DesayunoDTO>> list() {
		return ResponseEntity.ok(this.desayunoService.findAll());
	}

	@GetMapping("/{idDesayuno}")
	public ResponseEntity<Desayuno> findById(@PathVariable int idDesayuno) {
		Optional<Desayuno> optDesayuno = this.desayunoService.findById(idDesayuno);

		if (optDesayuno.isPresent()) {
			return ResponseEntity.ok(optDesayuno.get());
		}
		return ResponseEntity.notFound().build();
	}


	@PostMapping
	public ResponseEntity<Desayuno> create(@RequestBody Desayuno desayuno) {
		Desayuno savedDesayuno = this.desayunoService.create(desayuno);
		return new ResponseEntity<>(savedDesayuno, HttpStatus.CREATED);
	}

	@PutMapping("/{idDesayuno}")
	public ResponseEntity<Desayuno> update(@PathVariable int idDesayuno, @RequestBody Desayuno desayuno) {

		if (idDesayuno != desayuno.getId()) {
			return ResponseEntity.badRequest().build();
		}
		if (!this.desayunoService.existsDesayuno(idDesayuno)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(this.desayunoService.save(desayuno));
	}

	@DeleteMapping("/{idDesayuno}")
	public ResponseEntity<Desayuno> delete(@PathVariable int idDesayuno) {
		if (this.desayunoService.delete(idDesayuno)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/puntuacion")
	public ResponseEntity<List<DesayunoDTO>> puntuacionesDesayuno() {
		return ResponseEntity.ok(this.desayunoService.getByPuntuacion());
	}

	@GetMapping("/puntuacion/{idEstablecimiento}")
	public ResponseEntity<List<Desayuno>> puntuacionesDesayunoEstablecimiento(@PathVariable int idEstablecimiento) {
		return ResponseEntity.ok(this.desayunoService.getByPuntuacionFromEstablecimiento(idEstablecimiento));
	}

	@GetMapping("/precio/{idEstablecimiento}")
	public ResponseEntity<List<Desayuno>> precioDesayunoEstablecimiento(@PathVariable int idEstablecimiento) {
		return ResponseEntity.ok(this.desayunoService.getByPrecioFromEstablecimiento(idEstablecimiento));
	}
	
	@GetMapping("/establecimiento/{idEstablecimiento}")
	public ResponseEntity<List<Desayuno>> desayunosEstablecimiento(@PathVariable int idEstablecimiento){
		return ResponseEntity.ok(this.desayunoService.getAllFromEstablecimiento(idEstablecimiento));
	}
	
	@PutMapping("/actualizarImagen/{idDesayuno}")
	public ResponseEntity<Desayuno> imagenModificada(@PathVariable int idDesayuno, @PathVariable String imagen, @RequestBody Desayuno desayuno ){
		if (idDesayuno != desayuno.getId()) {
			return ResponseEntity.badRequest().build();
		}
		if (!this.desayunoService.existsDesayuno(idDesayuno)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.desayunoService.save(desayuno));
	}
	
}
