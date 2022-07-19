package com.egg.mypkg.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.mypkg.entidades.Acidificacion;
import com.egg.mypkg.entidades.Compuesto;
import com.egg.mypkg.entidades.Tiempo;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.repositorios.CompuestoRepositorio;
import com.egg.mypkg.repositorios.TiempoRepositorio;

@Service
public class TiempoServicio {

	
	@Autowired
	private TiempoRepositorio tiempoRepositorio;

	@Autowired
	private CompuestoRepositorio compuestoRepositorio;
	
	@Transactional
	public void guardarTiempo(String koh, String kcl, String ko3, String kno3, String idCompuesto) throws ErrorServicio {
		validar(koh, kcl, ko3, kno3);
		Tiempo tiempo = new Tiempo();
		
		tiempo.setConstanteOh(Double.parseDouble(koh));
		tiempo.setConstanteCl(Double.parseDouble(kcl));
		tiempo.setConstanteO3(Double.parseDouble(ko3));
		tiempo.setConstanteNo3(Double.parseDouble(kno3));
		if (koh.equalsIgnoreCase("0")) {
			tiempo.setTiempoOh(0.0d);
		}else {
			tiempo.setTiempoOh(tiempo.tiempoDeResidencia("oh", Double.parseDouble(koh)));
		}
		if (kcl.equalsIgnoreCase("0")) {
			tiempo.setTiempoCl(0.0d);
		}else {
			tiempo.setTiempoCl(tiempo.tiempoDeResidencia("cl", Double.parseDouble(kcl)));
		}
		if (ko3.equalsIgnoreCase("0")) {
			tiempo.setTiempoO3(0.0d);
		}else {
			tiempo.setTiempoO3(tiempo.tiempoDeResidencia("o3", Double.parseDouble(ko3)));
		}
		if (kno3.equalsIgnoreCase("0")) {
			tiempo.setTiempoNo3(0.0d);
		}else {
			tiempo.setTiempoNo3(tiempo.tiempoDeResidencia("kno3", Double.parseDouble(kno3)));
		}
		
		Compuesto compuesto = compuestoRepositorio.findById(idCompuesto).get();
		tiempo.setCompuesto(compuesto);
		tiempoRepositorio.save(tiempo);
	}
	
	@Transactional
	public void modificarTiempo(String id, String koh, String kcl, String ko3, String kno3) throws ErrorServicio {
		validar(koh, kcl, ko3, kno3);
		Optional<Tiempo> respuestaTiempo = tiempoRepositorio.findById(id);
		if (respuestaTiempo.isPresent()) {
			Tiempo tiempo = respuestaTiempo.get();
			tiempo.setConstanteOh(Double.parseDouble(koh));
			tiempo.setConstanteCl(Double.parseDouble(kcl));
			tiempo.setConstanteO3(Double.parseDouble(ko3));
			tiempo.setConstanteNo3(Double.parseDouble(kno3));
			
		}
	}
	
	public Tiempo validarConstantes(Tiempo tiempo, String koh, String kcl, String ko3, String kno3) throws ErrorServicio {
		if (koh.equalsIgnoreCase("0")) {
			tiempo.setTiempoOh(0.0d);
		}else {
			tiempo.setTiempoOh(tiempo.tiempoDeResidencia("oh", Double.parseDouble(koh)));
		}
		if (kcl.equalsIgnoreCase("0")) {
			tiempo.setTiempoCl(0.0d);
		}else {
			tiempo.setTiempoCl(tiempo.tiempoDeResidencia("cl", Double.parseDouble(kcl)));
		}
		if (ko3.equalsIgnoreCase("0")) {
			tiempo.setTiempoO3(0.0d);
		}else {
			tiempo.setTiempoO3(tiempo.tiempoDeResidencia("o3", Double.parseDouble(ko3)));
		}
		if (kno3.equalsIgnoreCase("0")) {
			tiempo.setTiempoNo3(0.0d);
		}else {
			tiempo.setTiempoNo3(tiempo.tiempoDeResidencia("kno3", Double.parseDouble(kno3)));
		}
		return tiempo;
	}
	
	public void validar(String koh, String kcl, String ko3, String kno3) throws ErrorServicio {
		if (koh == null || koh.isEmpty()) {
			throw new ErrorServicio("La constante kOH no puede ser nula o negativa");
		}
		if (kcl == null || kcl.isEmpty()) {
			throw new ErrorServicio("La constante kCl no puede ser nula o negativa");
		}
		if (ko3 == null || ko3.isEmpty()) {
			throw new ErrorServicio("La constante kO3 no puede ser nula o negativa");
		}
		if (kno3 == null || kno3.isEmpty()) {
			throw new ErrorServicio("La constante kNO3 no puede ser nula o negativa");
		}
	}
	
	
	public List<Tiempo> listarTiempos(){
		List<Tiempo> tiempos = new ArrayList();
		tiempos = tiempoRepositorio.findAll();
		return tiempos;
	}
	
	@Transactional
	public Tiempo buscarPorId(String id) throws ErrorServicio {
		return tiempoRepositorio.getOne(id);
	}
	
	@Transactional
	public void eliminarTiempo(String id) {
		Tiempo tiempo = tiempoRepositorio.getOne(id);
		tiempoRepositorio.delete(tiempo);
	}
}
