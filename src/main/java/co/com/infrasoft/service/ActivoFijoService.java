package co.com.infrasoft.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.infrasoft.documents.ActivoFijoDocument;
import co.com.infrasoft.repository.ActivoFijoRepository;

/**
 * Aqui esta toda la logica de los servicios
 * @author Diego Riveros
 *
 */
@Service
public class ActivoFijoService {
	
	@Autowired
	ActivoFijoRepository activoRepository;
	
	
	public ActivoFijoDocument encontrarActivoFijo(ObjectId id) {
		//TODO
		return null;
	}
	
	public ActivoFijoDocument actualizarActivoFijo(ObjectId id) {
		//TODO
		return null;
	}
	
	public void crearActivoFijo(ActivoFijoDocument activoFijo) {
		//TODO
	}

}
