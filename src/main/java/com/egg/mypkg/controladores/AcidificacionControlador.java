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
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.servicios.AcidificacionServicio;
import com.egg.mypkg.servicios.CompuestoServicio;



@Controller
@RequestMapping("/ap")
public class AcidificacionControlador {

	
	@Autowired
	private AcidificacionServicio acidificacionServicio;
	
	@Autowired
	private CompuestoServicio compuestoServicio;
	
	
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		List<Compuesto> compuestos = compuestoServicio.listarCompuestos();
		modelo.addAttribute("compuestos", compuestos);
		return "registro_ap.html";
	}
	
	@PostMapping("/registro")
	public String registro(ModelMap modelo, @RequestParam(required = false) Integer cloro,@RequestParam(required = false) Integer fluor,@RequestParam(required = false) Integer nitrogeno,@RequestParam(required = false) Integer azufre,@RequestParam(required = false) String pesoMolecular,@RequestParam(required = false) String idCompuesto) {
		try {
			acidificacionServicio.guardarAcidificacion(cloro, fluor, nitrogeno, azufre, pesoMolecular, idCompuesto);
			modelo.put("exito", "Se pudo calcular el AP correctamente");
			return "index.html";
		} catch (ErrorServicio e) {
			// TODO Auto-generated catch block
			
			modelo.put("error", "No se pudo calcular el AP");
			return "registro_ap.html";
		}
	}
	
	@GetMapping("/lista")
	public String listar(ModelMap modelo) {
		List<Acidificacion> potenciales = acidificacionServicio.listarPotenciales();
		modelo.addAttribute("potenciales",potenciales);
		return "potenciales_list.html";
		
	}
	
	@GetMapping("/modificarap/{id}")
	public String modificarAp(@PathVariable String id, ModelMap modelo) {
		Acidificacion acidificacion = acidificacionServicio.getOne(id);
		
		modelo.put("ap", acidificacion);
		List<Compuesto> compuestos = compuestoServicio.listarCompuestos();
		modelo.put("compuestos", compuestos);
		
		return "modificar_ap.html";
		
		
	}
	
	@PostMapping("/modificarap/{id}")
	public String modificarAp(@PathVariable String id,@RequestParam(required = false) Integer cloro,@RequestParam(required = false) Integer fluor,@RequestParam(required = false) Integer nitrogeno,@RequestParam(required = false) Integer azufre,@RequestParam(required = false) String pesoMolecular, ModelMap modelo) {
		try {
			acidificacionServicio.modificarAp(id, cloro, fluor, nitrogeno, azufre, pesoMolecular);
			return "redirect:../lista";
		} catch (ErrorServicio ex) {
			// TODO Auto-generated catch block
			modelo.put("error", "No se pudo editar");
			return "/";
		}
		
		
	}
	
	
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable String id) {
		
		try {
			acidificacionServicio.eliminarAp(id);
			return "redirect:../lista";
        } catch (Exception e) {
            return "/";
        }
		
		
	}
}
