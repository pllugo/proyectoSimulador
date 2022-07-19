package com.egg.mypkg.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Acidificacion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name="uuid", strategy = "uuid2")
	private String id;
	
	private Integer cloro;
	private Integer fluor;
	private Integer nitrogeno;
	private Integer azufre;
	private Double pesoMolecular;
	
	private Double potencialAp;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "compuesto_id")
	private Compuesto compuesto;
	
	public Acidificacion() {
		// TODO Auto-generated constructor stub
	}
	
	

	



	public Acidificacion(String id, Integer cloro, Integer fluor, Integer nitrogeno, Integer azufre,
			Double pesoMolecular, Double potencialAp, Compuesto compuesto) {
		this.id = id;
		this.cloro = cloro;
		this.fluor = fluor;
		this.nitrogeno = nitrogeno;
		this.azufre = azufre;
		this.pesoMolecular = pesoMolecular;
		this.potencialAp = potencialAp;
		this.compuesto = compuesto;
	}







	public String getId() {
		return id;
	}







	public void setId(String id) {
		this.id = id;
	}







	public Integer getCloro() {
		return cloro;
	}







	public void setCloro(Integer cloro) {
		this.cloro = cloro;
	}







	public Integer getFluor() {
		return fluor;
	}







	public void setFluor(Integer fluor) {
		this.fluor = fluor;
	}







	public Integer getNitrogeno() {
		return nitrogeno;
	}







	public void setNitrogeno(Integer nitrogeno) {
		this.nitrogeno = nitrogeno;
	}







	public Integer getAzufre() {
		return azufre;
	}







	public void setAzufre(Integer azufre) {
		this.azufre = azufre;
	}







	public Double getPesoMolecular() {
		return pesoMolecular;
	}







	public void setPesoMolecular(Double pesoMolecular) {
		this.pesoMolecular = pesoMolecular;
	}







	public Double getPotencialAp() {
		return potencialAp;
	}







	public void setPotencialAp(Double potencialAp) {
		this.potencialAp = potencialAp;
	}







	public Compuesto getCompuesto() {
		return compuesto;
	}







	public void setCompuesto(Compuesto compuesto) {
		this.compuesto = compuesto;
	}







	public Double potencial() {
		return potencialAp = (64.066d / pesoMolecular) * ((cloro + fluor + nitrogeno + 2.0d*azufre)/2d);
		
	}





	
	
	
}
