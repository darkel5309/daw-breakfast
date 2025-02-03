package com.daw.persistence.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "establecimiento")
@Getter
@Setter
@NoArgsConstructor
public class Establecimiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "VARCHAR(30)")
	private String nombre;

	@Column(columnDefinition = "VARCHAR(150)")
	private String descripcion;

	@Column(columnDefinition = "VARCHAR(255)")
	private String ubicacion;

	@Column(columnDefinition = "DECIMAL(3,2)")
	private Double puntuacion;
	
	@OneToMany(mappedBy = "establecimiento" )
	@JsonIgnore
	private List<Desayuno> desayunos;
}
