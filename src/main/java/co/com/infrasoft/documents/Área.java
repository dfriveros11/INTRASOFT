package co.com.infrasoft.documents;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Área {

	
	/**
	 * Nombre del área
	 */
	private String nombre;
	
	/**
	 * La lista de los activos fijos que la área tiene
	 */
	@JsonManagedReference(value="activoFijoÁrea")
	private List<ActivoFijoDocument> activosFijos;
	
	/**
	 * La ciudad a que eprtenece
	 */
	@JsonBackReference(value="áreaCiudad")
	private Ciudad ciudad;
	
	/**
	 * Constructor Vacio
	 */
	public Área() {}
	
	/**
	 * La clase builder para inicializar el Área
	 *
	 */
	public static class Builder {
		
		private String nombre;
		private List<ActivoFijoDocument> activosFijos;
		private Ciudad ciudad;
		
		public Builder(String nombre, Ciudad ciudad)  {
			super();
			this.nombre = nombre;
			this.ciudad = ciudad;
		}
		
		public Builder activosFijos(List<ActivoFijoDocument> activosFijos) {
			this.activosFijos = activosFijos;
			return this;
		}
		public Área build() {
			return new Área(this);
		}

	}
	
	/**
	 * Inicializa los valores
	 * 
	 * @param builder
	 */
	private Área(Builder builder) {
		this.nombre = builder.nombre;
		this.activosFijos = builder.activosFijos;
		this.ciudad = builder.ciudad;
	}
	
	@Override
	public String toString() {
		return "nombre=" + this.nombre + ", " + this.ciudad.toString();
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

	public Ciudad getCiudad() {
		return ciudad;
	}
}
