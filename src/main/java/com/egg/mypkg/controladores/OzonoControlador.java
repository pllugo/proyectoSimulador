package com.egg.mypkg.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.mypkg.entidades.Acidificacion;
import com.egg.mypkg.entidades.Compuesto;
import com.egg.mypkg.entidades.Ozono;
import com.egg.mypkg.entidades.Tiempo;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.repositorios.AcidificacionRepositorio;
import com.egg.mypkg.servicios.AcidificacionServicio;
import com.egg.mypkg.servicios.CompuestoServicio;
import com.egg.mypkg.servicios.OzonoServicio;
import com.egg.mypkg.servicios.TiempoServicio;

@Controller
@RequestMapping("/pocp")
public class OzonoControlador {
	
	@Autowired
	private OzonoServicio ozonoServicio;
	
	@Autowired
	private CompuestoServicio compuestoServicio;
	
	@Autowired
	private AcidificacionServicio acidificacionServicio;
	
	@Autowired
	private TiempoServicio tiempoServicio;
	
	
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo,@RequestParam(required = false) String id) {
		List<Compuesto> compuestos = compuestoServicio.listarCompuestos();
		modelo.addAttribute("compuestos", compuestos);
		List<Acidificacion> potenciales = acidificacionServicio.listarPotenciales();
		modelo.addAttribute("potenciales", potenciales);
		List<Tiempo> tiempos = tiempoServicio.listarTiempos();
		modelo.addAttribute("tiempos", tiempos);
		
		return "registro_pocp.html";
	}
	
	@PostMapping("/registro")
	public String registro(ModelMap modelo,@RequestParam(required = false) String clase_voc,@RequestParam(required = false) String region,@RequestParam(required = false) Integer numeroEnlaces,@RequestParam(required = false) Integer numeroCarbonos,@RequestParam(required = false) Integer numeroEnlacesProducto,@RequestParam(required = false) String constantekOhProductoNoReactivo,@RequestParam(required = false) String rendimientoProductoNoReactivo, @RequestParam(required = false) String idCompuesto, @RequestParam(required = false) String idAcidificacion, @RequestParam(required = false) String idTiempo, @RequestParam(required = false) String claseCov_p, @RequestParam(required = false) String covsConOzono, @RequestParam(required = false) String covAromatico, @RequestParam(required = false) String rendimientoCompuestoConOh) {
		try {
			ozonoServicio.guardarOzono(clase_voc, region, numeroEnlaces, numeroCarbonos, numeroEnlacesProducto, constantekOhProductoNoReactivo, rendimientoProductoNoReactivo, idCompuesto, idAcidificacion, idTiempo, claseCov_p, covsConOzono, covAromatico, rendimientoCompuestoConOh);
			modelo.put("exito", "Se ha calculado el POCP correctamente");
			return "index";
		} catch (ErrorServicio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modelo.put("error", "No se ha podido calcular el POCP");
			return "registro_pocp";
		}
	}
	
	@GetMapping("lista")
	public String listar(ModelMap modelo) {
		List<Ozono> potencialesPocp = ozonoServicio.listarOzono();
		modelo.addAttribute("potencialesPocp", potencialesPocp);
		return "pocp_list";
	}
	
	@GetMapping("/modificarpocp/{id}")
	public String modificarPocp(@PathVariable String id, ModelMap modelo) {
		Ozono pocp = ozonoServicio.getOne(id);
		modelo.put("pocp", pocp);
		return "modificar_pocp";
	}
	
	
	@PostMapping("/modificarpocp/{id}")
	public String modificarPocp(ModelMap modelo,@RequestParam(required = false) String id,@RequestParam(required = false) String clase_voc,@RequestParam(required = false) String region,@RequestParam(required = false) Integer numeroEnlaces,@RequestParam(required = false) Integer numeroCarbonos,
			Integer numeroEnlacesProducto,@RequestParam(required = false) String constantekOhProductoNoReactivo,@RequestParam(required = false) String rendimientoProductoNoReactivo,
			String constantekoh,@RequestParam(required = false) String pesoMolecular,@RequestParam(required = false) String constanteko3,@RequestParam(required = false) String claseCov_p,@RequestParam(required = false) String covsConOzono,@RequestParam(required = false) String covAromatico,@RequestParam(required = false) String rendimientoCompuestoConOh) {
		try {
			ozonoServicio.modificarOzono(id, clase_voc, region, numeroEnlaces, numeroCarbonos, numeroEnlacesProducto, constantekOhProductoNoReactivo, rendimientoProductoNoReactivo, constantekoh, pesoMolecular, constanteko3, claseCov_p, covsConOzono, covAromatico, rendimientoCompuestoConOh);
			return "redirect:../lista";
		} catch (ErrorServicio ex) {
			// TODO Auto-generated catch block
			modelo.put("error", ex.getMessage());
			return "modificar_pocp";
		}
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable String id) {
		
		try {
			ozonoServicio.eliminarPocp(id);
			return "redirect:../lista";
        } catch (Exception e) {
            return "/";
        }
		
		
	}
	
}
