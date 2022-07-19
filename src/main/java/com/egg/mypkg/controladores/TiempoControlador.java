package com.egg.mypkg.controladores;

import java.util.List;

import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.mypkg.entidades.Compuesto;
import com.egg.mypkg.entidades.Tiempo;
import com.egg.mypkg.errores.ErrorServicio;
import com.egg.mypkg.servicios.CompuestoServicio;
import com.egg.mypkg.servicios.TiempoServicio;

@Controller
@RequestMapping("/tiempo")
public class TiempoControlador {
	
	/*private Double constanteOh;
	private Double constanteCl;
	private Double constanteO3;
	private Double constanteNo3;
	
	private Double tiempoOh;
	private Double tiempoCl;
	private Double tiempoO3;
	private Double tiempoNo3;
	
	
	@OneToOne
	private Compuesto compuesto;*/
	
	@Autowired
	private TiempoServicio tiempoServicio;
	
	
	@Autowired
	private CompuestoServicio compuestoServicio;
	
	
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		List<Compuesto> compuestos = compuestoServicio.listarCompuestos();
		modelo.addAttribute("compuestos", compuestos);
		return "registro_tiempo.html";
	}
	
	@PostMapping("/registro")
	public String registro(ModelMap modelo, @RequestParam(required = false) String constanteOh,@RequestParam(required = false) String constanteCl,@RequestParam(required = false) String constanteO3,@RequestParam(required = false) String constanteNo3, @RequestParam(required = false) String idCompuesto) {
		try {
			tiempoServicio.guardarTiempo(constanteOh, constanteCl, constanteO3, constanteNo3, idCompuesto);
			modelo.put("exito", "El tiempo de residencia atmosférico se han calculado correctamente");
			return "index";
		} catch (ErrorServicio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modelo.put("error", "El tiempo de residencia atmosférico no se ha podido calcular");
			return "registro_tiempo";
		}
	}
	
	@GetMapping("lista")
	public String listar(ModelMap modelo) {
		List<Tiempo> tiempos = tiempoServicio.listarTiempos();
		modelo.put("tiempos", tiempos);
		return "tiempo_list";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable String id) {
		try {
			tiempoServicio.eliminarTiempo(id);
			return "redirect:../lista";
        } catch (Exception e) {
            return "/";
        }
	}
}
