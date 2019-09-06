package co.com.infrasoft.controller;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

/**
 * Esta clase se utiliza para encapsular la informacion para actulizar un activo fijo
 * @author Diego Riveros
 *
 */
public class ActualizarActivoFijoDTO {

	@NotNull(message="Debe haber un id del activo fijo a actualizar")
	private ObjectId id;
	
	@NotNull(message="El serial debe tener un valor para poder actualizar el activo fijo")
	@NotEmpty(message="El serial debe tener un valor para poder actualizar el activo fijo")
	private String serial;
	
	@NotNull(message="Debe haber una fecha baja para poder actualizar el activo fijo")
	private Date fechaBaja;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	
	
}
