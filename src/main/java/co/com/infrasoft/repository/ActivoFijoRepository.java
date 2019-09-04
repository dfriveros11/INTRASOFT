package co.com.infrasoft.repository;

import org.bson.types.ObjectId;
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
	 * Encuentra el activo fijo 
	 * @param id
	 * @return product
	 */
	public ActivoFijoDocument findBillById(String id);

}
