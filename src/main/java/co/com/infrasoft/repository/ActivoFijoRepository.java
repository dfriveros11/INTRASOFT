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
	 * Encuentra el activo fijo por el id de mongo 
	 * @param ObjectId
	 * @return ActivoFijo
	 */
	public ActivoFijoDocument findActivoFijoDocumentById(ObjectId id);
	
	
	/**
	 * Encuentra el activo fijo por el id  
	 * @param id
	 * @return ActivoFijo
	 */
	public ActivoFijoDocument findByActivoFijoId(int id);
	


}
