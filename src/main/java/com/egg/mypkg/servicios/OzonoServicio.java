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
public class OzonoServicio {

	@Autowired
	private OzonoRepositorio ozonoRepositorio;

	@Autowired
	private CompuestoRepositorio compuestoRepositorio;

	@Autowired
	private AcidificacionRepositorio acidificacionRepositorio;

	@Autowired
	private TiempoRepositorio tiempoRepositorio;

	@Autowired
	private AcidificacionServicio acidificacionServicio;

	@Autowired
	private TiempoServicio tiempoServicio;

	@Transactional
	public void guardarOzono(String clase_voc, String region, Integer numeroEnlaces, Integer numeroCarbonos,
			Integer numeroEnlacesProducto, String constantekOhProductoNoReactivo, String rendimientoProductoNoReactivo,
			String idCompuesto, String idAcidificacion, String idTiempo, String claseCov_p, String covsConOzono, String covAromatico, String rendimientoCompuestoConOh) throws ErrorServicio {
		validar(numeroEnlaces, numeroCarbonos, numeroEnlacesProducto, Double.parseDouble(constantekOhProductoNoReactivo), Double.parseDouble(rendimientoProductoNoReactivo), Double.parseDouble(rendimientoCompuestoConOh));
		Optional<String> respuestaAp = Optional.ofNullable(idAcidificacion);
		if (respuestaAp.isPresent()) {
			Acidificacion acidificacion = acidificacionServicio.buscarPorId(idAcidificacion);
			Optional<String> respuestaTiempo = Optional.ofNullable(idTiempo);
			if (respuestaTiempo.isPresent()) {
				Tiempo tiempo = tiempoServicio.buscarPorId(idTiempo);
				Compuesto compuesto = compuestoRepositorio.findById(idCompuesto).get();
				Ozono ozono = new Ozono();

				ozono.setClase_voc(clase_voc);
				ozono.setConstantekoh(tiempo.getConstanteOh());
				ozono.setRegion(region);
				ozono.setPesoMolecular(acidificacion.getPesoMolecular());
				ozono.setNumeroEnlaces(numeroEnlaces);
				ozono.setNumeroCarbonos(numeroCarbonos);
				ozono.setNumeroEnlacesProducto(numeroEnlacesProducto);
				ozono.setRendimientoProductoNoReactivo(Double.parseDouble(rendimientoProductoNoReactivo));
				ozono.setConstantekOhProductoNoReactivo(Double.parseDouble(constantekOhProductoNoReactivo));
				ozono.setClaseCov_p(claseCov_p);
				ozono.setCovsConOzono(covsConOzono);
				ozono.setCovAromatico(covAromatico);
				ozono.setConstanteko3(tiempo.getConstanteO3());
				ozono.setRendimientoCompuestoConOh(Double.parseDouble(rendimientoCompuestoConOh));
				ozono.setPotencialPocp(calcularPocp(clase_voc, region, acidificacion.getPesoMolecular(),
						tiempo.getConstanteOh(), numeroEnlaces, numeroCarbonos, Double.parseDouble(constantekOhProductoNoReactivo),
						Double.parseDouble(rendimientoProductoNoReactivo), numeroEnlacesProducto, claseCov_p, covsConOzono, covAromatico, Double.parseDouble(rendimientoCompuestoConOh)));

				ozono.setCompuesto(compuesto);
				ozonoRepositorio.save(ozono);
			}
		}

	}
	
	@Transactional
	public void modificarOzono(String id, String clase_voc, String region, Integer numeroEnlaces, Integer numeroCarbonos,
			Integer numeroEnlacesProducto, String constantekOhProductoNoReactivo, String rendimientoProductoNoReactivo,
			String constantekoh, String pesoMolecular, String constanteko3, String claseCov_p, String covsConOzono, String covAromatico, String rendimientoCompuestoConOh) throws ErrorServicio{
			
			Optional<Ozono> respuestaOzono = ozonoRepositorio.findById(id);
			if (respuestaOzono.isPresent()) {
				Ozono ozono = respuestaOzono.get();
				ozono.setClase_voc(clase_voc);
				ozono.setConstantekoh(Double.parseDouble(constantekoh));
				ozono.setPesoMolecular(Double.parseDouble(pesoMolecular));
				ozono.setNumeroEnlaces(numeroEnlaces);
				ozono.setNumeroCarbonos(numeroCarbonos);
				ozono.setNumeroEnlacesProducto(numeroEnlacesProducto);
				ozono.setRendimientoProductoNoReactivo(Double.parseDouble(rendimientoProductoNoReactivo));
				ozono.setConstantekOhProductoNoReactivo(Double.parseDouble(constantekOhProductoNoReactivo));
				ozono.setClaseCov_p(claseCov_p);
				ozono.setCovsConOzono(covsConOzono);
				ozono.setCovAromatico(covAromatico);
				ozono.setConstanteko3(Double.parseDouble(constanteko3));
				ozono.setPotencialPocp(calcularPocp(clase_voc, region, Double.parseDouble(pesoMolecular),
						Double.parseDouble(constantekoh), numeroEnlaces, numeroCarbonos, Double.parseDouble(constantekOhProductoNoReactivo),
						Double.parseDouble(rendimientoProductoNoReactivo), numeroEnlacesProducto, claseCov_p, covsConOzono, covAromatico, Double.parseDouble(rendimientoCompuestoConOh)));
				ozono.setCompuesto(ozono.getCompuesto());
				ozonoRepositorio.save(ozono);
			}
	}

	public void validar(Integer numeroEnlaces, Integer numeroCarbonos,
			Integer numeroEnlacesProducto, Double constantekOhProductoNoReactivo, Double rendimientoProductoNoReactivo, Double rendimientoCompuestoConOh)
			throws ErrorServicio {
		
		if (numeroEnlaces == null || numeroEnlaces <= 0) {
			throw new ErrorServicio("Los numeros de enlaces no puede ser nulo ni negativo");
		}
		if (numeroCarbonos == null || numeroCarbonos <= 0) {
			throw new ErrorServicio("Los numeros de carbonos no puede ser nulo ni negativo");
		}
		if (numeroEnlacesProducto == null || numeroEnlacesProducto < 0) {
			throw new ErrorServicio("Los numeros de enlaces del producto no puede ser nulo ni negativo");
		}

		if (constantekOhProductoNoReactivo == null || constantekOhProductoNoReactivo < 0.0d) {
			throw new ErrorServicio("La constante del producto con OH no puede ser nulo ni negativo");
		}
		if (rendimientoProductoNoReactivo == null || rendimientoProductoNoReactivo < 0.0d) {
			throw new ErrorServicio("El rendimiento no puede ser nulo ni negativo");
		}
		if (rendimientoCompuestoConOh == null || rendimientoCompuestoConOh < 0.0d) {
			throw new ErrorServicio("El rendimiento no puede ser nulo ni negativo");
		}
		
	}

	public Double parametroA(String clase_cov, String region) {
		// TODO Auto-generated method stub

		String[] vectorA = {"aliphatics", "aromatics with 0 alkyl substituents", "aromatics with 1 alkyl substituents",
				"aromatics with 2 alkyl substituents", "aromatics with 3 alkyl substituents" };
		Double[] vectorEuropa = { 100.0, 66.0, 130.0, 173.0, 206.0 };
		Double[] vectorUsa = { 154.0, 25.0, 320.0, 427.0, 509.0 };
		return buscarParametro(clase_cov, region, vectorA, vectorEuropa, vectorUsa);
	}

	public Double parametroB(String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa")) {
			resultado = 4.0d;
		} else if (region.equalsIgnoreCase("usa")) {
			resultado = 1.0d;
		}
		return resultado;
	}

	public Double parametroAlfa(String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa")) {
			resultado = 0.56d;
		} else if (region.equalsIgnoreCase("usa")) {
			resultado = 0.37d;
		}
		return resultado;

	}

	public Double parametroC(String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa")) {
			resultado = 0.0038d;
		} else if (region.equalsIgnoreCase("usa")) {
			resultado = 0.0041d;
		}
		return resultado;
	}

	public Double parametroBeta(String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa")) {
			resultado = 2.7d;
		} else if (region.equalsIgnoreCase("usa")) {
			resultado = 2.7d;
		}
		return resultado;
	}

	public Double parametroD(String clase_cov, String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa") && clase_cov.equalsIgnoreCase("aliphatics")) {
			resultado = 0.38d;
		} else if (region.equalsIgnoreCase("usa") && clase_cov.equalsIgnoreCase("aliphatics")) {
			resultado = 0.25d;
		}
		return resultado;
	}

	public Double parametroEpsilon(String clase_cov, String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa") && clase_cov.equalsIgnoreCase("aliphatics")) {
			resultado = 0.16d;
		} else if (region.equalsIgnoreCase("usa") && clase_cov.equalsIgnoreCase("aliphatics")) {
			resultado = 0.18d;
		}
		return resultado;
	}

	public Double parametroP(String covsFotolizan, String region) {
		// TODO Auto-generated method stub
		String[] vectorA = { "aldehydes/ketones", "alpha-dicarbonyls", "cycloketones"};
		Double[] vectorEuropa = { 14.0d, 67.0d, 0.0d};
		Double[] vectorUsa = { 10.0d, 124.0d, 0.0d, 0.0d };
		return buscarParametro(covsFotolizan, region, vectorA, vectorEuropa, vectorUsa);
	}

	public Double parametroE(String covsConOzono, String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa") && covsConOzono.equalsIgnoreCase("alkenes/unsaturated oxygenates")) {
			resultado = 20.9d;
		} else if (region.equalsIgnoreCase("usa") && covsConOzono.equalsIgnoreCase("alkenes/unsaturated oxygenates")) {
			resultado = 22.0d;
		}
		
		return resultado;
	}

	public Double parametroLambda(String covsConOzono, String region) {
		// TODO Auto-generated method stub
		Double resultado = 0.0d;
		if (region.equalsIgnoreCase("europa") && covsConOzono.equalsIgnoreCase("alkenes/unsaturated oxygenates")) {
			resultado = 0.043d;
		} else if (region.equalsIgnoreCase("usa") && covsConOzono.equalsIgnoreCase("alkenes/unsaturated oxygenates")) {
			resultado = 0.15d;
		}
		return resultado;
	}

	public Double parametroQ(String covAromatico, String region) {
		// TODO Auto-generated method stub
		String[] vectorA = {"benzaldehydes/styrenes", "hydroxyarenes"};
		Double[] vectorEuropa = { 74.0d, 41.0d };
		Double[] vectorUsa = { 80.0d, 0.0d };
		return buscarParametro(covAromatico, region, vectorA, vectorEuropa, vectorUsa);
	}

	public Double buscarParametro(String clase_cov, String region, String[] vectorCov, Double[] vectorEuropa,
			Double[] vectorUsa) {
		Double resultado = 0.0d;
		for (int i = 0; i < vectorCov.length; i++) {
			if (vectorCov[i].equalsIgnoreCase(clase_cov)) {
				if (region.equalsIgnoreCase("europa")) {
					resultado = vectorEuropa[i];
				} else if (region.equalsIgnoreCase("usa")) {
					resultado = vectorUsa[i];
				}
			}
		}
		return resultado;
	}

	public Double calculoGammaS(Integer numeroEnlaces, Double pesoMolecular) {
		String enlacesString = String.valueOf(numeroEnlaces);

		Double enlacesDouble = Double.parseDouble(enlacesString);
		return ((enlacesDouble / 6.0d) * (28.05d / pesoMolecular));

	}

	public Double calculoR(Integer numeroEnlaces, Double constantekoh, String region) {
		String enlacesString = String.valueOf(numeroEnlaces);
		Double enlacesDouble = Double.parseDouble(enlacesString);
		return (1.0d
				- (1.0d / ((constantekoh / 0.0000000000078d) * (6.0d / enlacesDouble) * parametroB(region) + 1.0d)));
	}

	public Double calculoF(String clase_voc, String region, Double constantekoh, Double constantekOhProductoNoReactivo,
			Double rendimientoProductoNoReactivo, Integer numeroEnlaces, Integer numeroEnlacesProducto) {
		if (constantekOhProductoNoReactivo < 2e-12) {
			return 1.0;
		} else {
			return ((numeroEnlaces - (rendimientoProductoNoReactivo * numeroEnlacesProducto)) / numeroEnlaces) * Math
					.pow(0, parametroD(clase_voc, region) * (1 - (1000000000000d * constantekOhProductoNoReactivo / 2)
							* Math.pow(1000000000000d * constantekoh, parametroEpsilon(clase_voc, region))));
		}
	}

	public Double calculoS(Integer numeroCarbonos, String region) {
		Double resultado = -1.0d * parametroC(region) * Math.pow(numeroCarbonos, parametroBeta(region));
		Double resultadoExponencial = Math.exp(resultado);
		Double resultado1 = 1.0d - parametroAlfa(region);
		return resultado1 * resultadoExponencial + parametroAlfa(region);
	}

	public Double calculoRo3(String clase_cov, String region, Double constanteO3, Double rendimientoOh) {
		Double resultadoRo3 = 0.0d;
		Double resultadoE = parametroE(clase_cov, region);
		if (resultadoE != 0.0d) {
			Double resultadoLambda = parametroLambda(clase_cov, region);
			Double miu = Math.pow((constanteO3 / 0.00000000000000000155d) * (rendimientoOh / 0.98d), resultadoLambda);
			return Math.pow(resultadoE, miu);
		} else {
			System.out.println(resultadoRo3);
			return resultadoRo3;
		}

	}

	public Double calcularPocp(String clase_voc, String region, Double pesoMolecular, Double constantekoh,
			Integer numeroEnlaces, Integer numeroCarbonos, Double constantekOhProductoNoReactivo,
			Double rendimientoProductoNoReactivo, Integer numeroEnlacesProducto, String claseCov_p, String covsConOzono, String covAromatico, Double rendimientoCompuestoConOh) {
		Double potencialPocp;
		potencialPocp = (parametroA(clase_voc, region) * calculoGammaS(numeroEnlaces, pesoMolecular)
				* calculoR(numeroEnlaces, constantekoh, region) * calculoS(numeroCarbonos, region)
				* calculoF(clase_voc, region, constantekoh, constantekOhProductoNoReactivo,
						rendimientoProductoNoReactivo, numeroEnlaces, numeroEnlacesProducto))
				+ parametroP(claseCov_p, region)
				+ calculoRo3(covsConOzono, region, constantekOhProductoNoReactivo, rendimientoCompuestoConOh)
				+ parametroQ(covAromatico, region);
		return potencialPocp;

	}
	
	public Ozono getOne(String id) {
		return ozonoRepositorio.getOne(id);
	}
	
	public List<Ozono> listarOzono(){
		List<Ozono> listaRespuesta = new ArrayList();
		listaRespuesta = ozonoRepositorio.findAll();
		return listaRespuesta;
	}
	
	public List<Double> listarPotencialesPocp(){
		List<Double> listaDePocp = new ArrayList();
		List<Ozono> listarTodos = listarOzono();
		for (Ozono pocp : listarTodos) {
			listaDePocp.add(pocp.getPotencialPocp());
		}
		return listaDePocp;
	}
	
	public List<String> listarNombresDeCompuestos(){
		List<String> listaDeNombres = new ArrayList();
		List<Ozono> listarTodos = listarOzono();
		for (Ozono ozono : listarTodos) {
			listaDeNombres.add(ozono.getCompuesto().getNombre());
		}
		return listaDeNombres;
	}
	
	@Transactional
	public void eliminarPocp(String id) {
		ozonoRepositorio.deleteById(id);
	}

}
