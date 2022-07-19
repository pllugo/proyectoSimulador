package com.egg.mypkg.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.mypkg.entidades.Acidificacion;
import com.egg.mypkg.entidades.Compuesto;
import com.egg.mypkg.entidades.Ozono;
import com.egg.mypkg.entidades.Tiempo;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.repositorios.AcidificacionRepositorio;
import com.egg.mypkg.repositorios.CompuestoRepositorio;
import com.egg.mypkg.repositorios.OzonoRepositorio;
import com.egg.mypkg.repositorios.TiempoRepositorio;

@Service
public class CompuestoServicio {

	
	@Autowired
	private CompuestoRepositorio compuestoRepositorio;
	
	
	
	
	@Transactional
	public void guardarCompuesto(String nombre, String formula) throws ErrorServicio {
		
		validar(nombre, formula);
		Compuesto compuesto = new Compuesto();
		compuesto.setNombre(nombre);
		compuesto.setFormula(formula);
		
		compuestoRepositorio.save(compuesto);
		
	}
	
	@Transactional
	public Compuesto modificarCompuesto(String id, String nombre, String formula) throws ErrorServicio{
		validar(nombre, formula);
		try {
			Compuesto compuesto = new Compuesto();
			if (id != null) {
				Optional<Compuesto> respuesta = compuestoRepositorio.findById(id);
				if (respuesta.isPresent()) {
					compuesto = respuesta.get();
					compuesto.setNombre(nombre);
					compuesto.setFormula(formula);
					return compuestoRepositorio.save(compuesto);
				}
			}
		} catch (Exception ex) {
			// TODO: handle exception
			throw new ErrorServicio(ex.getMessage());
		}
		
		
		return null;
	}
	
	
	public void validar(String nombre, String formula) throws ErrorServicio {
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El nombre no puede ser nula o vacia");
		}
		if (formula == null || formula.isEmpty()) {
			throw new ErrorServicio("La formula no puede ser nula o vacia");
		}
	}
	
	
	
	public List<Compuesto> listarCompuestos(){
		List<Compuesto> listaCompuestos = new ArrayList();
		Compuesto compuesto = new Compuesto();
		
		compuestoRepositorio.findAll().forEach(com->{
			compuesto.setId(com.getId());
		compuesto.setNombre(com.getNombre());
		compuesto.setFormula(com.getFormula());
		listaCompuestos.add(compuesto);
		});
				
		return listaCompuestos;
	}
	
	
	
	public List<String> listarNombre() {
		List<String> listarTodos = new ArrayList();
		List<Compuesto> listaDeNombres = listarCompuestos();
		for (Compuesto cov : listaDeNombres) {
			listarTodos.add(cov.getNombre());
		}
		return listarTodos;
	}
	
	public Compuesto buscarPorId(String id) {
		Compuesto compuesto = compuestoRepositorio.findById(id).get();
		return compuesto;
	}
	
	@Transactional
	public void eliminarCov(String id) throws ErrorServicio{
		
		if (compuestoRepositorio.existsById(id)) {
			compuestoRepositorio.delete(compuestoRepositorio.findById(id).get());
		}else {
			throw new ErrorServicio("es nulo");
		}
		
	}
}
