package com.egg.mypkg.controladores;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.mypkg.servicios.AcidificacionServicio;
import com.egg.mypkg.servicios.CompuestoServicio;
import com.egg.mypkg.servicios.OzonoServicio;

@Controller
@RequestMapping("/graph")
public class GraphControlador {

	@Autowired
	private AcidificacionServicio acidificacionServicio;
	
	@Autowired
	private OzonoServicio ozonoServicio;
	
	@Autowired
	private CompuestoServicio compuestoServicio;
	
	@GetMapping("/display")
	public String barGraph(ModelMap modelo) {
		List<Double> lista = acidificacionServicio.potenciales();
		List<Double> listaPocp =ozonoServicio.listarPotencialesPocp();
		List<String> listaCompAp = acidificacionServicio.listaDeNombres();
		List<String> listaCompPocp = ozonoServicio.listarNombresDeCompuestos();
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("listaPocp", listaPocp);
		modelo.addAttribute("listaCompAp", listaCompAp);
		modelo.addAttribute("listaCompPocp", listaCompPocp);
		return "grafica";
	}
}
