package com.daw.persistence.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "desayuno")
@Getter
@Setter
@NoArgsConstructor
public class Desayuno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "id_establecimiento", nullable = false)
	private Integer idEstablecimiento;

	@Column(columnDefinition = "VARCHAR(30)")
	private String nombre;

	@Column(columnDefinition = "DECIMAL(5,2)")
	private Double precio;

	@Column(columnDefinition = "VARCHAR(255)")
	private String imagen;

	@Column(columnDefinition = "DECIMAL(3,2)")
	private Double puntuacion;
	
	@ManyToOne
	@JoinColumn(name="id_establecimiento", referencedColumnName = "id", insertable = false, updatable = false)
	private Establecimiento establecimiento;
	
	@OneToMany(mappedBy ="desayuno")
	@JsonIgnore
	private List<Review> reviews;
	
}
