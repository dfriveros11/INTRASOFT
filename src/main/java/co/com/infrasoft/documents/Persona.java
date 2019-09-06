package co.com.infrasoft.documents;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class Persona {

	/**
	 * Nombre del área
	 */
	private String nombre;
	
	
	/**
	 * La lista de los activos fijos que la persona tiene
	 */
	@JsonManagedReference(value="activoFijoPersona")
	private List<ActivoFijoDocument> activosFijos;
	
	/**
	 * Constructor Vacio
	 */
	public Persona() {}
	
	/**
	 * La clase builder para inicializar el Persona
	 *
	 */
	public static class Builder {
		
		private String nombre;
		private List<ActivoFijoDocument> activosFijos;
		
		public Builder(String nombre) {
			super();
			this.nombre = nombre;
		}
		
		public Builder activosFijos(List<ActivoFijoDocument> activosFijos) {
			this.activosFijos = activosFijos;
			return this;
		}
		
		public Persona build() {
			return new Persona(this);
		}

	}
	
	/**
	 * Inicializa los valores
	 * 
	 * @param builder
	 */
	private Persona(Builder builder) {
		this.nombre = builder.nombre;
		this.activosFijos = builder.activosFijos;
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
		((ObjectNode) rootNode).put("nombre", this.nombre);
		return rootNode;
	}

	public String getNombre() {
		return nombre;
	}

	public List<ActivoFijoDocument> getActivosFijos() {
		return activosFijos;
	}
}
