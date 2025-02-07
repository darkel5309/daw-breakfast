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
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<Desayuno> create(@RequestParam Desayuno desayuno) {
		return new ResponseEntity<Desayuno>(this.desayunoService.create(desayuno), HttpStatus.CREATED);
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
	public ResponseEntity<Desayuno> delete(@PathVariable int idDesayuno){
		if(this.desayunoService.delete(idDesayuno)){
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
