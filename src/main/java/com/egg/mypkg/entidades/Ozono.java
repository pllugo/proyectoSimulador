package com.egg.mypkg.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.mypkg.interfaces.OzonoServicio;

@Entity
public class Ozono implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name="uuid", strategy = "uuid2")
	private String id;
	
	
	private String clase_voc;
	private String region;
	private Integer numeroEnlaces;
	private Integer numeroCarbonos;
	private Double constantekOhProductoNoReactivo;
	private Double rendimientoProductoNoReactivo;
	private Integer numeroEnlacesProducto;
	private String claseCov_p;
	private String covsConOzono;
	private String covAromatico;

	private Double pesoMolecular;
	private Double constantekoh;
	private Double constanteko3;
	private Double rendimientoCompuestoConOh;
	private Double potencialPocp;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "compuesto_id")
	private Compuesto compuesto;
	
	
	public Ozono() {
		// TODO Auto-generated constructor stub
	}


	public Ozono(String id, String clase_voc, String region, Integer numeroEnlaces, Integer numeroCarbonos,
			Double constantekOhProductoNoReactivo, Double rendimientoProductoNoReactivo, Integer numeroEnlacesProducto,
			String claseCov_p, String covsConOzono, String covAromatico, Double pesoMolecular, Double constantekoh,
			Double constanteko3, Double rendimientoCompuestoConOh, Double potencialPocp, Compuesto compuesto) {
		this.id = id;
		this.clase_voc = clase_voc;
		this.region = region;
		this.numeroEnlaces = numeroEnlaces;
		this.numeroCarbonos = numeroCarbonos;
		this.constantekOhProductoNoReactivo = constantekOhProductoNoReactivo;
		this.rendimientoProductoNoReactivo = rendimientoProductoNoReactivo;
		this.numeroEnlacesProducto = numeroEnlacesProducto;
		this.claseCov_p = claseCov_p;
		this.covsConOzono = covsConOzono;
		this.covAromatico = covAromatico;
		this.pesoMolecular = pesoMolecular;
		this.constantekoh = constantekoh;
		this.constanteko3 = constanteko3;
		this.rendimientoCompuestoConOh = rendimientoCompuestoConOh;
		this.potencialPocp = potencialPocp;
		this.compuesto = compuesto;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getClase_voc() {
		return clase_voc;
	}


	public void setClase_voc(String clase_voc) {
		this.clase_voc = clase_voc;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public Integer getNumeroEnlaces() {
		return numeroEnlaces;
	}


	public void setNumeroEnlaces(Integer numeroEnlaces) {
		this.numeroEnlaces = numeroEnlaces;
	}


	public Integer getNumeroCarbonos() {
		return numeroCarbonos;
	}


	public void setNumeroCarbonos(Integer numeroCarbonos) {
		this.numeroCarbonos = numeroCarbonos;
	}


	public Double getConstantekOhProductoNoReactivo() {
		return constantekOhProductoNoReactivo;
	}


	public void setConstantekOhProductoNoReactivo(Double constantekOhProductoNoReactivo) {
		this.constantekOhProductoNoReactivo = constantekOhProductoNoReactivo;
	}


	public Double getRendimientoProductoNoReactivo() {
		return rendimientoProductoNoReactivo;
	}


	public void setRendimientoProductoNoReactivo(Double rendimientoProductoNoReactivo) {
		this.rendimientoProductoNoReactivo = rendimientoProductoNoReactivo;
	}


	public Integer getNumeroEnlacesProducto() {
		return numeroEnlacesProducto;
	}


	public void setNumeroEnlacesProducto(Integer numeroEnlacesProducto) {
		this.numeroEnlacesProducto = numeroEnlacesProducto;
	}


	public String getClaseCov_p() {
		return claseCov_p;
	}


	public void setClaseCov_p(String claseCov_p) {
		this.claseCov_p = claseCov_p;
	}


	public String getCovsConOzono() {
		return covsConOzono;
	}


	public void setCovsConOzono(String covsConOzono) {
		this.covsConOzono = covsConOzono;
	}


	public String getCovAromatico() {
		return covAromatico;
	}


	public void setCovAromatico(String covAromatico) {
		this.covAromatico = covAromatico;
	}


	public Double getPesoMolecular() {
		return pesoMolecular;
	}


	public void setPesoMolecular(Double pesoMolecular) {
		this.pesoMolecular = pesoMolecular;
	}


	public Double getConstantekoh() {
		return constantekoh;
	}


	public void setConstantekoh(Double constantekoh) {
		this.constantekoh = constantekoh;
	}


	public Double getConstanteko3() {
		return constanteko3;
	}


	public void setConstanteko3(Double constanteko3) {
		this.constanteko3 = constanteko3;
	}


	public Double getRendimientoCompuestoConOh() {
		return rendimientoCompuestoConOh;
	}


	public void setRendimientoCompuestoConOh(Double rendimientoCompuestoConOh) {
		this.rendimientoCompuestoConOh = rendimientoCompuestoConOh;
	}


	public Double getPotencialPocp() {
		return potencialPocp;
	}


	public void setPotencialPocp(Double potencialPocp) {
		this.potencialPocp = potencialPocp;
	}


	public Compuesto getCompuesto() {
		return compuesto;
	}


	public void setCompuesto(Compuesto compuesto) {
		this.compuesto = compuesto;
	}



	
	
}
