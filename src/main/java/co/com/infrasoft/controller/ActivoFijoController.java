package co.com.infrasoft.controller;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import co.com.infrasoft.documents.ActivoFijoDocument;
import co.com.infrasoft.service.ActivoFijoService;

/**
 * 
 * Aqui esta los end points
 * 
 * @author Diego Riveros
 *
 */
@Controller
@RequestMapping("/activosfijos")
public class ActivoFijoController {
	
	private ActivoFijoService activoFijoservice;
	
	/**
	 * Constructor
	 * @param activoFijoService
	 */
	@Autowired
	public ActivoFijoController(final ActivoFijoService activoFijoService) {
		this.activoFijoservice = activoFijoService;
	}
	
	@PostMapping("/create")
	@ResponseBody
	public void crearActivoFijo(@RequestBody @Valid ActivoFijoDocument activoFijo) {
		//TODO
	}
	
	
	@PutMapping("/update")
	@ResponseBody
	public void actualizarActivo(@RequestBody @Valid ActivoFijoDocument activoFijo) {
		//TODO
	}
	
	@GetMapping("/getbyid/{id}")
	@ResponseBody
	public ActivoFijoDocument obtenerActivoFijo(@PathVariable("id") ObjectId id) {
		//TODO
		return null;
	}
	
	

}
