package co.com.infrasoft.repository;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.infrasoft.IntrasoftApplication;
import co.com.infrasoft.documents.ActivoFijoDocument;
import co.com.infrasoft.documents.convertidor.ZonedDateTimeEscribirConvertidor;
import co.com.infrasoft.documents.convertidor.ZonedDateTimeLeerConvertidor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { IntrasoftApplication.class })
public class ActivoFijoRepositoryTest {

	@Autowired
	private ActivoFijoRepository activoFijoRepository;

	private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM yyyy");

	private final ZoneId zona = ZoneId.of("America/Bogota");
	
	@Autowired
    private MongoTemplate mongoTemplate;

	@Before
	public void crearActivosFijos() {
		activoFijoRepository.deleteAll();
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTimeEscribirConvertidor zoned = new ZonedDateTimeEscribirConvertidor();
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(2);

		ActivoFijoDocument activoFijo = new ActivoFijoDocument.Builder(1, "Prueba", "Maquinaria", "123", 1, 100, 100,
				100, 100, 986.000, zoned.convert(fechaCompra), zoned.convert(fechaBaja), "activo", "azul").build();

		activoFijoRepository.insert(activoFijo);

	}


	@Test
	public void obtenerActivoFijo() {
		ActivoFijoDocument activoFijo = activoFijoRepository.findByActivoFijoId(1);
		assertEquals("El id es distinto", 1, activoFijo.getActivoFijoId());
		assertEquals("El nombre es distinto", "Prueba", activoFijo.getNombre());
		assertEquals("El tipo es distinto", "MAQUINARIA", activoFijo.getTipo());
		assertEquals("El serial es distinto", "123", activoFijo.getSerial());
		assertEquals("El numero interno del inventario es distinto", new BigDecimal(1),
				new BigDecimal(activoFijo.getNumInterInventario()));
		assertEquals("El peso es distinto", 100, activoFijo.getPeso());
		assertEquals("El alto es distinto", 100, activoFijo.getAlto());
		assertEquals("El ancho es distinto", 100, activoFijo.getAncho());
		assertEquals("El largo es distinto", 100, activoFijo.getLargo());
		assertEquals("El valor de compra es distinto", 986.000, activoFijo.getValorCompra(), 0.0000);

		ZonedDateTimeLeerConvertidor zoned = new ZonedDateTimeLeerConvertidor();

		assertEquals("El formato de la fecha de compra esta mal guardada",
				format.format(LocalDateTime.now(ZoneOffset.UTC)),
				format.format(zoned.convert(activoFijo.getFechaCompra())));
		assertEquals("El formato de la fecha de baja esta mal guardada",
				format.format(LocalDateTime.now(ZoneOffset.UTC).plusMonths(2)),
				format.format(zoned.convert(activoFijo.getFechaBaja())));
		assertEquals("El estado actual esta mal", "ACTIVO", activoFijo.getEstadoActual());
		assertEquals("EL color rojo es incorrecto", "AZUL", activoFijo.getColor());
	}

	@Test(expected = AssertionError.class)
	public void construirMalActivoFijoTiṕo() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTimeEscribirConvertidor zoned = new ZonedDateTimeEscribirConvertidor();
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(2);
		
		new ActivoFijoDocument.Builder(1, "Prueba", "No existe", "123", 1, 100, 100,
				100, 100, 986.000, zoned.convert(fechaCompra), zoned.convert(fechaBaja), "activo", "azul").build();
		
	}
	
	@Test(expected = AssertionError.class)
	public void construirMalActivoFijoEstadoActual() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTimeEscribirConvertidor zoned = new ZonedDateTimeEscribirConvertidor();
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(2);
		
		new ActivoFijoDocument.Builder(1, "Prueba", "Maquinaria", "123", 1, 100, 100,
				100, 100, 986.000, zoned.convert(fechaCompra), zoned.convert(fechaBaja), "No existe", "azul").build();
		
	}
	
	@Test(expected = AssertionError.class)
	public void construirMalActivoFijoColor() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTimeEscribirConvertidor zoned = new ZonedDateTimeEscribirConvertidor();
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(2);
		
		new ActivoFijoDocument.Builder(1, "Prueba", "Maquinaria", "123", 1, 100, 100,
				100, 100, 986.000, zoned.convert(fechaCompra), zoned.convert(fechaBaja), "activo", "No existe").build();
		
	}
	
	
	@Test
	public void actualizarSerialInternoYFechaBaja() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTimeEscribirConvertidor zoned = new ZonedDateTimeEscribirConvertidor();
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(4);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("activoFijoId").is(1));
		Update update = new Update();
		update.set("serial", "67890");
		update.set("fechaBaja", zoned.convert(fechaBaja));
		mongoTemplate.findAndModify(query, update, ActivoFijoDocument.class);
		
		ActivoFijoDocument activoFijoModificado = activoFijoRepository.findByActivoFijoId(1);
		
		ZonedDateTimeLeerConvertidor zonedleer = new ZonedDateTimeLeerConvertidor();
		assertEquals("El serial no cambio", "67890", activoFijoModificado.getSerial());
		assertEquals("La fecha de baja no cambio",
				"04 Jan 2020",
				format.format(zonedleer.convert(activoFijoModificado.getFechaBaja())));
	}
	
	/**
	 * Con esto se compreueba que hay un bug al actualizar un documento ya existen, hay un reporte de este bug. 
	 * Una forma de solucionarlo se propone en el Test con nombre: solucionActualizarSerialInternoYFechaBaja
	 */
	@Test
	public void comprobarActualizarSerialInternoYFechaBaja() {
		ActivoFijoDocument activoFijoModificado = activoFijoRepository.findByActivoFijoId(1);
		
		ZonedDateTimeLeerConvertidor zonedleer = new ZonedDateTimeLeerConvertidor();
		assertEquals("El serial no cambio", "67890", activoFijoModificado.getSerial());
		assertEquals("La fecha de baja no cambio",
				"04 Jan 2020",
				format.format(zonedleer.convert(activoFijoModificado.getFechaBaja())));
	}
	
	
	@Test
	public void solucionActualizarSerialInternoYFechaBaja() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTimeEscribirConvertidor zoned = new ZonedDateTimeEscribirConvertidor();
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(4);
		
		activoFijoRepository.deleteActivoFijo(1);
		
		ActivoFijoDocument activoFijo = new ActivoFijoDocument.Builder(1, "Prueba", "Maquinaria", "67890", 1, 100, 100,
				100, 100, 986.000, zoned.convert(fechaCompra), zoned.convert(fechaBaja), "activo", "azul").build();
		activoFijoRepository.insert(activoFijo);
		
		ActivoFijoDocument activoFijoModificado = activoFijoRepository.findByActivoFijoId(1);
		
		ZonedDateTimeLeerConvertidor zonedleer = new ZonedDateTimeLeerConvertidor();
		assertEquals("El serial no cambio", "67890", activoFijoModificado.getSerial());
		assertEquals("La fecha de baja no cambio",
				"04 Jan 2020",
				format.format(zonedleer.convert(activoFijoModificado.getFechaBaja())));
	}
}
