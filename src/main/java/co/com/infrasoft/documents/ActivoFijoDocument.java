package co.com.infrasoft.documents;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import co.com.infrasoft.documents.convertidor.ZonedDateConvertidor;
import co.com.infrasoft.documents.utilities.Color;
import co.com.infrasoft.documents.utilities.EstadoActual;
import co.com.infrasoft.documents.utilities.Tipo;

/**
 * @author Diego Riveros Se utiliza un patron builder para la creacion de
 *         objetos
 */
@Document
public class ActivoFijoDocument {

	/**
	 * Mongo Product's ID
	 */
	@Id
	private ObjectId id;

	/**
	 * Nombre del Activo
	 */
	@NotNull(message = "El activo fijo debe tener un nombre")
	@NotEmpty(message = "El activo fijo debe tener un nombre")
	private String nombre;

	/**
	 * Tipo del Activo
	 */
	@NotNull(message = "El activo fijo debe tener un tipo")
	@NotEmpty(message = "El activo fijo debe tener un tipo")
	private String tipo;

	/**
	 * Serial del Activo
	 */
	@NotNull(message = "El activo fijo debe tener un serial")
	@NotEmpty(message = "El activo fijo debe tener un serial")
	private String serial;

	/**
	 * Numero Interno del Inventario del Activo
	 */
	@NotNull(message = "El activo fijo debe tener un numero de interno de inventario")
	@Min(value = 1L, message = "The value must be positive")
	private long numInterInventario;

	/**
	 * Peso del Activo
	 */
	@NotNull(message = "El activo fijo debe tener un peso")
	@Min(value = 1L, message = "The value must be positive")
	private int peso;

	/**
	 * Alto del Activo
	 */
	@NotNull(message = "El activo fijo debe tener un altura")
	@Min(value = 1L, message = "The value must be positive")
	private int alto;

	/**
	 * Ancho del Activo
	 */
	@NotNull(message = "El activo fijo debe tener un ancho")
	@Min(value = 1L, message = "The value must be positive")
	private int ancho;

	/**
	 * Largo del Activo
	 */
	@NotNull(message = "El activo fijo debe tener un largo")
	@Min(value = 1L, message = "The value must be positive")
	private int largo;

	/**
	 * Valor de Compra del Activo
	 */
	@NotNull(message = "El activo fijo debe tener un valor de compra")
	@Min(value = 1L, message = "The value must be positive")
	private double valorCompra;

	/**
	 * Fecha de Compra del Activo
	 */
	@NotNull(message = "El activo fijo debe tener un fecha de compra")
	private Date fechaCompra;

	/**
	 * Fecha de Baja del Activo
	 */
	@NotNull(message = "El activo fijo debe tener un decha de baja")
	private Date fechaBaja;

	/**
	 * Estado Actual del Activo
	 */
	@NotNull(message = "El activo fijo debe tener un estado actual")
	@NotEmpty(message = "El activo fijo debe tener un estado actual")
	private String estadoActual;

	/**
	 * Color del Activo
	 */
	@NotNull(message = "El activo fijo debe tener un color")
	@NotEmpty(message = "El activo fijo debe tener un color")
	private String color;

	public ActivoFijoDocument() {
	}

	/**
	 * La clase builder para inicializar el Activo Fijo
	 *
	 */
	public static class Builder {

		private String nombre;
		private String tipo;
		private String serial;
		private long numInterInventario;
		private int peso;
		private int alto;
		private int ancho;
		private int largo;
		private double valorCompra;
		private Date fechaCompra;
		private Date fechaBaja;
		private String estadoActual;
		private String color;

		/**
		 * Clase constructora
		 * 
		 * @param activoFijoId
		 * @param nombre
		 * @param tipo
		 * @param serial
		 * @param numInterInventario
		 * @param peso
		 * @param alto
		 * @param ancho
		 * @param largo
		 * @param valorCompra
		 * @param fechaCompra
		 * @param fechaBaja
		 * @param estadoActual
		 * @param color
		 */
		public Builder(String nombre, String tipo, String serial, long numInterInventario, int peso, int alto,
				int ancho, int largo, double valorCompra, Date fechaCompra, Date fechaBaja, String estadoActual,
				String color) {
			super();
			this.nombre = nombre;
			this.tipo = escogerTipo(tipo);
			this.serial = serial;
			this.numInterInventario = numInterInventario;
			this.peso = peso;
			this.alto = alto;
			this.ancho = ancho;
			this.largo = largo;
			this.valorCompra = valorCompra;
			this.fechaCompra = fechaCompra;
			this.fechaBaja = fechaBaja;
			this.estadoActual = escogerEstadoActual(estadoActual);
			this.color = escogerColor(color);
		}

		/**
		 * Escoger el tipo de activo
		 * 
		 * @param tipo
		 * @return el string del tipo escogido
		 * @throws AssertionError cuando no existe el tipo
		 */
		private String escogerTipo(String tipo) throws AssertionError {
			String tipoEs = "";
			if (Tipo.BIENESINMUEBLES.toString().toLowerCase().equals(tipo.toLowerCase())) {
				tipoEs = tipo.toUpperCase();
			} else if (Tipo.MAQUINARIA.toString().toLowerCase().equals(tipo.toLowerCase())) {
				tipoEs = tipo.toUpperCase();
			} else if (Tipo.MATERIALOFICINA.toString().toLowerCase().equals(tipo.toLowerCase())) {
				tipoEs = tipo.toUpperCase();
			} else {
				throw new AssertionError("Tipo Desconocido: " + tipo);
			}
			return tipoEs;
		}

		/**
		 * Seleciona el estado actual del activo fijo
		 * 
		 * @param estadoActual
		 * @return
		 * @throws AssertionError cuando no existe el estado actual
		 */
		private String escogerEstadoActual(String estadoActual) throws AssertionError {
			String estadoAtualEs = "";
			if (EstadoActual.ACTIVO.toString().toLowerCase().equals(estadoActual.toLowerCase())) {
				estadoAtualEs = estadoActual.toUpperCase();
			} else if (EstadoActual.DADODEBAJA.toString().toLowerCase().equals(estadoActual.toLowerCase())) {
				estadoAtualEs = estadoActual.toUpperCase();
			} else if (EstadoActual.ENREPARACIÓN.toString().toLowerCase().equals(estadoActual.toLowerCase())) {
				estadoAtualEs = estadoActual.toUpperCase();
			} else if (EstadoActual.DISPONIBLE.toString().toLowerCase().equals(estadoActual.toLowerCase())) {
				estadoAtualEs = estadoActual.toUpperCase();
			} else if (EstadoActual.ASIDNADO.toString().toLowerCase().equals(estadoActual.toLowerCase())) {
				estadoAtualEs = estadoActual.toUpperCase();
			} else {
				throw new AssertionError("Estado Actual Desconocido: " + estadoActual);
			}
			return estadoAtualEs;
		}

		private String escogerColor(String color) {
			String colorEs = "";
			if (Color.AZUL.toString().toLowerCase().equals(color.toLowerCase())) {
				colorEs = color.toUpperCase();
			} else {
				throw new AssertionError("Color Desconocido: " + color);
			}
			return colorEs;
		}

		public ActivoFijoDocument build() {
			return new ActivoFijoDocument(this);
		}

	}

	/**
	 * Inicializa los valores
	 * 
	 * @param builder
	 */
	private ActivoFijoDocument(Builder builder) {
		this.nombre = builder.nombre;
		this.tipo = builder.tipo;
		this.serial = builder.serial;
		this.numInterInventario = builder.numInterInventario;
		this.peso = builder.peso;
		this.alto = builder.alto;
		this.ancho = builder.ancho;
		this.largo = builder.largo;
		this.valorCompra = builder.valorCompra;
		this.fechaCompra = builder.fechaCompra;
		this.fechaBaja = builder.fechaBaja;
		this.estadoActual = builder.estadoActual;
		this.color = builder.color;
	}

	public ObjectId getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public String getSerial() {
		return serial;
	}

	public long getNumInterInventario() {
		return numInterInventario;
	}

	public int getPeso() {
		return peso;
	}

	public int getAlto() {
		return alto;
	}

	public int getAncho() {
		return ancho;
	}

	public int getLargo() {
		return largo;
	}

	public double getValorCompra() {
		return valorCompra;
	}

	public ZonedDateTime getFechaCompra() {
		return ZonedDateConvertidor.convertDateToZoned(fechaCompra);
	}

	public ZonedDateTime getFechaBaja() {
		return ZonedDateConvertidor.convertDateToZoned(fechaBaja);
	}

	public String getEstadoActual() {
		return estadoActual;
	}

	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return "id=" + this.id + ", nombre=" + this.nombre + ", tipo=" + this.tipo + ", serial=" + this.serial
				+ ", numero interno de inventario=" + this.numInterInventario + ", peso=" + this.peso + ", alto="
				+ this.alto + ", ancho=" + this.ancho + ", largo=" + this.largo + ", valorCompra=" + this.valorCompra
				+ ", fechaCompra=" + this.fechaCompra + ", fechaBaja=" + this.fechaBaja + ", estadoActual="
				+ this.estadoActual + ", color=" + this.color;
	}

	/**
	 * Entrega la representaion de json de la clase
	 * @return JsonNode
	 */
	public JsonNode toJson() {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.createObjectNode();
		((ObjectNode) rootNode).put("id", this.id.toString());
		((ObjectNode) rootNode).put("nombre", this.nombre);
		((ObjectNode) rootNode).put("tipo", this.tipo);
		((ObjectNode) rootNode).put("serial", this.serial);
		((ObjectNode) rootNode).put("numero interno de inventario", this.numInterInventario + "");
		((ObjectNode) rootNode).put("peso", this.peso + "");
		((ObjectNode) rootNode).put("alto", this.alto + "");
		((ObjectNode) rootNode).put("ancho", this.ancho + "");
		((ObjectNode) rootNode).put("largo", this.largo + "");
		((ObjectNode) rootNode).put("valorCompra", this.valorCompra + "");
		((ObjectNode) rootNode).put("fechaCompra", this.fechaCompra.toString());
		((ObjectNode) rootNode).put("fechaBaja", this.fechaBaja.toString());
		((ObjectNode) rootNode).put("estadoActual", this.estadoActual);
		((ObjectNode) rootNode).put("color", this.color);
		return rootNode;
	}

}
