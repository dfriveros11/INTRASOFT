package co.com.infrasoft.service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import co.com.infrasoft.documents.ActivoFijoDocument;
import co.com.infrasoft.documents.convertidor.ZonedDateConvertidor;
import co.com.infrasoft.repository.ActivoFijoRepository;

/**
 * Aqui esta toda la logica de los servicios
 * 
 * @author Diego Riveros
 *
 */
@Service
public class ActivoFijoService {

	private ActivoFijoRepository activoRepository;

	private MongoTemplate mongoTemplate;

	/**
	 * Constructor
	 * 
	 * @param activoFijoRepository
	 * @param mongoTemplate
	 */
	@Autowired
	public ActivoFijoService(final ActivoFijoRepository activoFijoRepository, final MongoTemplate mongoTemplate) {
		this.activoRepository = activoFijoRepository;
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * Obtiene el activoFijo
	 * @param id
	 * @return
	 * @throws NullPointerException
	 */
	public ActivoFijoDocument obtenerActivoFijo(ObjectId id) throws NullPointerException {
		return encontraActivoFijo(id);
	}

	/**
	 * Obtiene el activo Fijo
	 * @param id
	 * @return
	 * @throws NullPointerException
	 * El objeto no existe
	 */
	private ActivoFijoDocument encontraActivoFijo(ObjectId id) throws NullPointerException {
		try {
			Optional<ActivoFijoDocument> activo = activoRepository.findById(id);
			return activo.orElseThrow(() -> new NullPointerException());
		}catch (NullPointerException e) {
			throw new NullPointerException("El objeto con el id: " + id + " no existe en la base de datos");
		}
	}

	/**
	 * Actualiza el activo fijo
	 * @param id
	 * @param serial
	 * @param date
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public void actualizarActivoFijo(ObjectId id, String serial, LocalDateTime date)
			throws NullPointerException, IllegalArgumentException {
		ActivoFijoDocument activoFijo = this.obtenerActivoFijo(id);
		ZonedDateTime fechaBaja = date.atZone(ZonedDateConvertidor.zona());
		final ZonedDateTime fechaCompra = activoFijo.getFechaCompra();
		verficarFechas(fechaCompra, fechaBaja);
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		Update update = new Update();
		update.set("serial", serial);
		update.set("fechaBaja",
				ZonedDateConvertidor.convertZonedToDate(ZonedDateTime.of(date, ZonedDateConvertidor.zona())));
		mongoTemplate.findAndModify(query, update, ActivoFijoDocument.class);

	}

	/**
	 * Verifica la regla de negocio en donde la fecha de compra > fecha de baja
	 * @param fechaCompra
	 * @param fechaBaja
	 * @throws IllegalArgumentException
	 * En el caso que la fecha de compra < lafecha de baja
	 */
	private void verficarFechas(ZonedDateTime fechaCompra, ZonedDateTime fechaBaja) throws IllegalArgumentException {
		if (fechaCompra.compareTo(fechaBaja) < 0) {
			throw new IllegalArgumentException("La fecha de compra debe ser superior a la fecha de baja. Se encontraron los sigueintes valores "
					+ "fecha de compra: " + fechaCompra + " fecha de baja: " + fechaBaja);
		}
	}

	/**
	 * Crea un Activo Fijo, verificando la regla de negocio en donde la fecha de compra > fecha de baja
	 * @param activoFijo
	 * @return
	 * @throws IllegalArgumentException
	 *  En el caso que la fecha de compra < lafecha de baja
	 */
	public ObjectId crearActivoFijo(ActivoFijoDocument activoFijo) throws IllegalArgumentException {
		verficarFechas(activoFijo.getFechaCompra(), activoFijo.getFechaBaja());
		return activoRepository.insert(activoFijo).getId();
	}

	/**
	 * Encuentra todos los activos fijos
	 * @return List<ActivoFijoDocument>
	 */
	public List<ActivoFijoDocument> obtenerActivosFijos() {
		return activoRepository.findAll();
	}

}
