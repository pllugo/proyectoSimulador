package com.egg.mypkg.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Compuesto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name="uuid", strategy = "uuid2")
	private String id;
	
	private String nombre;
	private String formula;
	
	
	
	
	public Compuesto() {
		// TODO Auto-generated constructor stub
	}



	public Compuesto(String id, String nombre, String formula) {
		this.id = id;
		this.nombre = nombre;
		this.formula = formula;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getFormula() {
		return formula;
	}



	public void setFormula(String formula) {
		this.formula = formula;
	}

	
	



	



	



	

	
	
	

}
