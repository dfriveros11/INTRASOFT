package co.com.infrasoft.documents;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Ciudad {

	/**
	 * Nombre del área
	 */
	private String nombre;
	
	/**
	 * Las áreas que tiene la ciudad
	 */
	@JsonManagedReference(value="áreaCiudad")
	private List<Área> areas;
	
	/**
	 * Constructor Vacio
	 */
	public Ciudad() {}
	
	/**
	 * La clase builder para inicializar el Ciudad
	 *
	 */
	public static class Builder {
		
		private String nombre;
		private List<Área> areas;
		
		public Builder(String nombre) {
			super();
			this.nombre = nombre;
		}
		
		public Builder areas( List<Área> areas) {
			this.areas = areas;
			return this;
		}
		
		public Ciudad build() {
			return new Ciudad(this);
		}

	}
	
	/**
	 * Inicializa los valores
	 * 
	 * @param builder
	 */
	private Ciudad(Builder builder) {
		this.nombre = builder.nombre;
		this.areas = builder.areas;
	}
	
	@Override
	public String toString() {
		return "nombre=" + this.nombre;
	}

	/**
	 * Entrega la representaion de json de la clase
	 * @return JsonNode
	 */
	public JsonNode toJson() {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.createObjectNode();
		((ObjectNode) rootNode).put("Nombre Ciudad", this.nombre);
		return rootNode;
	}

	public String getNombre() {
		return nombre;
	}

	public List<Área> getAreas() {
		return areas;
	}
	
}
