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

import com.egg.mypkg.entidades.Compuesto;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.servicios.AcidificacionServicio;
import com.egg.mypkg.servicios.CompuestoServicio;
import com.egg.mypkg.servicios.OzonoServicio;
import com.egg.mypkg.servicios.TiempoServicio;

@Controller
@RequestMapping("/compuesto")
public class CompuestoControlador {

	@Autowired
	private CompuestoServicio compuestoServicio;
	
	@Autowired
	private AcidificacionServicio acidificacionServicio;
	
	@Autowired
	private OzonoServicio ozonoServicio;
	
	@Autowired
	private TiempoServicio tiempoServicio;
	
	@GetMapping("/registrar")
	public String registrar() {
		return "registro.html";
	}
	
	@PostMapping("/registro")
	public String registro(@RequestParam(required = false) String nombre,@RequestParam(required = false) String formula, ModelMap modelo) {
		try {
			compuestoServicio.guardarCompuesto(nombre, formula);
			modelo.put("exito", "El compuesto fue cargado exitosamente");
			return "index";
		} catch (ErrorServicio e) {
			// TODO Auto-generated catch block
			modelo.put("exito", "El compuesto fue cargado exitosamente");
			e.printStackTrace();
			return "registro";
		}
		
	}
	
	
	@GetMapping("/lista")
	public String listar(ModelMap modelo) {
		List<Compuesto> compuestos = compuestoServicio.listarCompuestos();
		modelo.addAttribute("compuestos", compuestos);
		return "compuesto_list";
	}
	
	
	@GetMapping("/modificarcov/{id}")
	public String modificar(@PathVariable String id, ModelMap modelo) {
		Compuesto compuesto = compuestoServicio.buscarPorId(id);
		
		return "modificar_cov";
	}
	
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable String id) {
		
		try {
			
			compuestoServicio.eliminarCov(id);
			return "redirect:../lista";
        } catch (Exception e) {
            return "redirect:../lista";
        }
		
		
	}
}
