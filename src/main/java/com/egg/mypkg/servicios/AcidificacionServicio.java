package com.egg.mypkg.servicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.mypkg.entidades.Acidificacion;
import com.egg.mypkg.entidades.Compuesto;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.repositorios.AcidificacionRepositorio;
import com.egg.mypkg.repositorios.CompuestoRepositorio;

@Service
public class AcidificacionServicio {

	
	@Autowired
	private AcidificacionRepositorio acidificacionRepositorio;
	
	@Autowired
	private CompuestoRepositorio compuestoRepositorio;
	
	
	
	@Autowired
	private CompuestoServicio compuestoServicio;
	
	@Transactional
	public void guardarAcidificacion(Integer cloro, Integer fluor, Integer nitrogeno, Integer azufre, String pesoMolecular, String idCompuesto) throws ErrorServicio {
		validar(cloro, fluor, nitrogeno, azufre, Double.parseDouble(pesoMolecular));
		Acidificacion acidificacion = new Acidificacion();
		acidificacion.setCloro(cloro);
		acidificacion.setFluor(fluor);
		acidificacion.setNitrogeno(nitrogeno);
		acidificacion.setAzufre(azufre);
		acidificacion.setPesoMolecular(Double.parseDouble(pesoMolecular));
		acidificacion.setPotencialAp(acidificacion.potencial());
		Compuesto compuesto = compuestoRepositorio.findById(idCompuesto).get();
		acidificacion.setCompuesto(compuesto);
		acidificacionRepositorio.save(acidificacion);

	}

	@Transactional
	public void modificarAp(String id, Integer cloro, Integer fluor, Integer nitrogeno, Integer azufre,
			String pesoMolecular) throws ErrorServicio {
		validar(cloro, fluor, nitrogeno, azufre, Double.parseDouble(pesoMolecular));
		Optional<String> respuestaAp = Optional.ofNullable(id);

		if (respuestaAp.isPresent()) {
			Acidificacion acidificacion = buscarPorId(id);

			acidificacion.setCloro(cloro);
			acidificacion.setFluor(fluor);
			acidificacion.setNitrogeno(nitrogeno);
			acidificacion.setAzufre(azufre);
			acidificacion.setPesoMolecular(Double.parseDouble(pesoMolecular));
			acidificacion.setPotencialAp(acidificacion.potencial());
			acidificacion.setCompuesto(acidificacion.getCompuesto());
			acidificacionRepositorio.save(acidificacion);
		}


	}

	public void validar(Integer cloro, Integer fluor, Integer nitrogeno, Integer azufre, Double pesoMolecular) throws ErrorServicio {
		if (cloro == null) {
			throw new ErrorServicio("El nombre no puede ser nula o vacia");
		}
		if (fluor == null) {
			throw new ErrorServicio("La formula no puede ser nula o vacia");
		}
		if (nitrogeno == null) {
			throw new ErrorServicio("La formula no puede ser nula o vacia");
		}
		if (azufre == null) {
			throw new ErrorServicio("La formula no puede ser nula o vacia");
		}
		if (pesoMolecular == null || pesoMolecular <=0) {
			throw new ErrorServicio("La formula no puede ser nula o vacia");
		}
	}
	
	
	
	public List<Acidificacion> listarPotenciales(){
		List<Acidificacion> potenciales = new ArrayList();
		potenciales = acidificacionRepositorio.findAll();
		return potenciales;
	}
	
	@Transactional
	public Acidificacion buscarPorId(String id) throws ErrorServicio {
		return acidificacionRepositorio.getOne(id);
	}
	
	public List<Double> potenciales() {
		List<Double> listaAp = new ArrayList();
		List<Acidificacion> listarTodos = listarPotenciales();
		for (Acidificacion ap : listarTodos) {
			listaAp.add(ap.getPotencialAp());
		}
		return listaAp;
	}
	
	
	public Acidificacion getOne(String id) {
		return acidificacionRepositorio.getOne(id);
	}
	
	@Transactional
	public void eliminarAp(String id) {
		acidificacionRepositorio.deleteById(id);
	}
	
	public List<String> listaDeNombres(){
		List<String> listaNombres = new ArrayList();
		List<Acidificacion> listarTodos = listarPotenciales();
		for (Acidificacion acidificacion : listarTodos) {
			listaNombres.add(acidificacion.getCompuesto().getNombre());
		}
		return listaNombres;
	}
}
