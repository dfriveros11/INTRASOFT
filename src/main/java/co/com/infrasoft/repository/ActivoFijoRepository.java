package co.com.infrasoft.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.com.infrasoft.documents.ActivoFijoDocument;

/**
 * Maneja las preguntas a la base de datos
 * @author Diego Riveros
 *
 */
@Repository
public interface ActivoFijoRepository extends MongoRepository<ActivoFijoDocument, ObjectId>{
	
	/**
	 * Elimina el activo fijo con el id pasado
	 * @param activoFijoId
	 */
	@DeleteQuery(value="{'activoFijoId' :?0}")
	public void deleteActivoFijo(int activoFijoId);
}

