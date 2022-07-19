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
public class Tiempo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name="uuid", strategy = "uuid2")
	private String id;
	
	private Double constanteOh;
	private Double constanteCl;
	private Double constanteO3;
	private Double constanteNo3;
	
	private Double tiempoOh;
	private Double tiempoCl;
	private Double tiempoO3;
	private Double tiempoNo3;
	
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "compuesto_id")
	private Compuesto compuesto;
	
	
	public Tiempo() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	








	public Tiempo(String id, Double constanteOh, Double constanteCl, Double constanteO3, Double constanteNo3,
			Double tiempoOh, Double tiempoCl, Double tiempoO3, Double tiempoNo3, Compuesto compuesto) {
		this.id = id;
		this.constanteOh = constanteOh;
		this.constanteCl = constanteCl;
		this.constanteO3 = constanteO3;
		this.constanteNo3 = constanteNo3;
		this.tiempoOh = tiempoOh;
		this.tiempoCl = tiempoCl;
		this.tiempoO3 = tiempoO3;
		this.tiempoNo3 = tiempoNo3;
		this.compuesto = compuesto;
	}












	public String getId() {
		return id;
	}












	public void setId(String id) {
		this.id = id;
	}












	public Double getConstanteOh() {
		return constanteOh;
	}












	public void setConstanteOh(Double constanteOh) {
		this.constanteOh = constanteOh;
	}












	public Double getConstanteCl() {
		return constanteCl;
	}












	public void setConstanteCl(Double constanteCl) {
		this.constanteCl = constanteCl;
	}












	public Double getConstanteO3() {
		return constanteO3;
	}












	public void setConstanteO3(Double constanteO3) {
		this.constanteO3 = constanteO3;
	}












	public Double getConstanteNo3() {
		return constanteNo3;
	}












	public void setConstanteNo3(Double constanteNo3) {
		this.constanteNo3 = constanteNo3;
	}












	public Double getTiempoOh() {
		return tiempoOh;
	}












	public void setTiempoOh(Double tiempoOh) {
		this.tiempoOh = tiempoOh;
	}












	public Double getTiempoCl() {
		return tiempoCl;
	}












	public void setTiempoCl(Double tiempoCl) {
		this.tiempoCl = tiempoCl;
	}












	public Double getTiempoO3() {
		return tiempoO3;
	}












	public void setTiempoO3(Double tiempoO3) {
		this.tiempoO3 = tiempoO3;
	}












	public Double getTiempoNo3() {
		return tiempoNo3;
	}












	public void setTiempoNo3(Double tiempoNo3) {
		this.tiempoNo3 = tiempoNo3;
	}












	public Compuesto getCompuesto() {
		return compuesto;
	}












	public void setCompuesto(Compuesto compuesto) {
		this.compuesto = compuesto;
	}












	public Double tiempoDeResidencia(String oxidante, Double constante) {

		Double tiempo = 0.0d;
		if (oxidante.equalsIgnoreCase("oh") || oxidante.equalsIgnoreCase("OH")) {

			tiempo = 1 / (constante * 2000000d);
		}
		if (oxidante.equalsIgnoreCase("cl") || oxidante.equalsIgnoreCase("Cl")) {

			tiempo = 1 / (constante * 10000d);
		}
		if (oxidante.equalsIgnoreCase("o3") || oxidante.equalsIgnoreCase("O3")) {

			tiempo = 1 / (constante* 500000000d);

		}
		if (oxidante.equalsIgnoreCase("no3") || oxidante.equalsIgnoreCase("NO3")) {
			tiempo = 1 / (constante * 700000000000d);

		}
		return tiempo;
		
	}












	@Override
	public int hashCode() {
		return Objects.hash(compuesto);
	}












	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tiempo other = (Tiempo) obj;
		return Objects.equals(compuesto, other.compuesto);
	}
	
	
	

}
