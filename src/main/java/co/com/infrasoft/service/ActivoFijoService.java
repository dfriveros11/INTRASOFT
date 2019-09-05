package co.com.infrasoft.service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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

	public ActivoFijoDocument encontrarActivoFijo(ObjectId id) throws NullPointerException, IllegalArgumentException {
		return encontraActivoFijo(id);
	}

	private ActivoFijoDocument encontraActivoFijo(ObjectId id) throws NullPointerException, IllegalArgumentException {
		try {
			Optional<ActivoFijoDocument> activo = activoRepository.findById(id);
			return activo.orElseThrow(() -> new NullPointerException());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("El id: " + id + "no tiene el formato correcto");
		} catch (NullPointerException e) {
			throw new NullPointerException("El objeto con el id: " + id + " no existe en la base de datos");
		}
	}

	public void actualizarActivoFijo(ObjectId id, String serial, LocalDateTime date)
			throws NullPointerException, IllegalArgumentException {
		ActivoFijoDocument activoFijo = this.encontrarActivoFijo(id);
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

	private void verficarFechas(ZonedDateTime fechaCompra, ZonedDateTime fechaBaja) throws IllegalArgumentException {
		if (fechaCompra.compareTo(fechaBaja) < 0) {
			throw new IllegalArgumentException("La fecha de compra debe ser superior a la fecha de baja"
					+ "fecha de compra: " + fechaCompra + " fecha de baja: " + fechaBaja);
		}
	}

	public ObjectId crearActivoFijo(ActivoFijoDocument activoFijo) throws IllegalArgumentException {
		verficarFechas(activoFijo.getFechaCompra(), activoFijo.getFechaBaja());
		return activoRepository.insert(activoFijo).getId();
	}

}
