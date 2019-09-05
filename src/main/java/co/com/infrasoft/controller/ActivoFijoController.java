package co.com.infrasoft.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import co.com.infrasoft.documents.ActivoFijoDocument;
import co.com.infrasoft.documents.convertidor.ZonedDateConvertidor;
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
	private final ObjectMapper mapper; 
	
	/**
	 * Constructor
	 * @param activoFijoService
	 */
	@Autowired
	public ActivoFijoController(final ActivoFijoService activoFijoService) {
		this.activoFijoservice = activoFijoService;
		this.mapper = new ObjectMapper();
	}
	
	/**
	 * Crea un activo Fijo
	 * @param activoFijo
	 * @return ResponseEntity<JsonNode
	 */
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<JsonNode> crearActivoFijo(@RequestBody @Valid ActivoFijoDocument activoFijo) {
		JsonNode rootNode = mapper.createObjectNode();
		try {
			ObjectId id = activoFijoservice.crearActivoFijo(activoFijo);
			((ObjectNode) rootNode).put("http_status", "202");
			((ObjectNode) rootNode).put("message", "el objeto fue correctamente creado");
			((ObjectNode) rootNode).put("id", id.toString());
			return new ResponseEntity<JsonNode>(rootNode, HttpStatus.ACCEPTED);
		}catch (IllegalArgumentException e) {
			((ObjectNode) rootNode).put("http_status", "500");
			((ObjectNode) rootNode).put("message", e.getMessage());
			return new ResponseEntity<JsonNode>(rootNode, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Actualiza un activo fijo
	 * @param actualizarActioFijo
	 * @return ResponseEntity<JsonNode>
	 */
	@PutMapping("/update")
	@ResponseBody
	public ResponseEntity<JsonNode> actualizarActivo(@RequestBody @Valid ActualizarActivoFijoDTO actualizarActioFijo) {
		JsonNode rootNode = mapper.createObjectNode();
		try {
			LocalDateTime fecha = actualizarActioFijo.getFechaBaja().toInstant().atZone(ZonedDateConvertidor.zona()).toLocalDateTime();
			activoFijoservice.actualizarActivoFijo(new ObjectId(actualizarActioFijo.getId()), actualizarActioFijo.getSerial(), fecha);
			((ObjectNode) rootNode).put("http_status", "202");
			((ObjectNode) rootNode).put("message", "El Activo se actualizo correctamente");
			((ObjectNode) rootNode).put("id", actualizarActioFijo.getId());
			((ObjectNode) rootNode).put("serial", actualizarActioFijo.getSerial());
			((ObjectNode) rootNode).put("fechaBaja", fecha.atZone(ZonedDateConvertidor.zona()).toString());
			return new ResponseEntity<JsonNode>(rootNode, HttpStatus.ACCEPTED);
		}catch(NullPointerException e) {
			((ObjectNode) rootNode).put("http_status", "404");
			((ObjectNode) rootNode).put("message", e.getMessage());
			return new ResponseEntity<JsonNode>(rootNode, HttpStatus.NOT_FOUND);
		}catch (IllegalArgumentException e) {
			((ObjectNode) rootNode).put("http_status", "500");
			((ObjectNode) rootNode).put("message", e.getMessage());
			return new ResponseEntity<JsonNode>(rootNode, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Obtiene un Activo Fijo
	 * @param id
	 * @return ResponseEntity<JsonNode>
	 */
	@GetMapping("/getbyid/{id}")
	@ResponseBody
	public ResponseEntity<JsonNode> obtenerActivoFijo(@PathVariable("id") ObjectId id) {
		JsonNode rootNode = mapper.createObjectNode();
		try {
			ActivoFijoDocument activoFijo = activoFijoservice.obtenerActivoFijo(id);
			((ObjectNode) rootNode).put("http_status", "202");
			((ObjectNode) rootNode).put("message", "El Activo fijo se encontro");
			((ObjectNode) rootNode).set("Activo FIjo", activoFijo.toJson());
			return new ResponseEntity<JsonNode>(rootNode, HttpStatus.ACCEPTED);
		}catch(NullPointerException e) {
			((ObjectNode) rootNode).put("http_status", "404");
			((ObjectNode) rootNode).put("message", e.getMessage());
			return new ResponseEntity<JsonNode>(rootNode, HttpStatus.NOT_FOUND);
		}catch (IllegalArgumentException e) {
			((ObjectNode) rootNode).put("http_status", "500");
			((ObjectNode) rootNode).put("message", e.getMessage());
			return new ResponseEntity<JsonNode>(rootNode, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
