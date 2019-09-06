package co.com.infrasoft.repository;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.infrasoft.IntrasoftApplication;
import co.com.infrasoft.documents.ActivoFijoDocument;
import co.com.infrasoft.documents.Ciudad;
import co.com.infrasoft.documents.Persona;
import co.com.infrasoft.documents.Área;
import co.com.infrasoft.documents.convertidor.ZonedDateConvertidor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { IntrasoftApplication.class })
public class ActivoFijoRepositoryTest {

	@Autowired
	private ActivoFijoRepository activoFijoRepository;

	private final DateTimeFormatter format = ZonedDateConvertidor.dateFormat();

	private final ZoneId zona = ZonedDateConvertidor.zona();

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void crearActivoFijoTest() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(2);
		
		
		ActivoFijoDocument activoFijoGuardar = new ActivoFijoDocument.Builder("Prueba", "Maquinaria", "123", 1, 100,
				100, 100, 100, 986.000, ZonedDateConvertidor.convertZonedToDate(fechaCompra),
				ZonedDateConvertidor.convertZonedToDate(fechaBaja), "activo", "azul").build();

		ActivoFijoDocument activoFijoComprobar = activoFijoRepository.insert(activoFijoGuardar);

		Optional<ActivoFijoDocument> activoFijoOptinal = activoFijoRepository.findById(activoFijoComprobar.getId());
		ActivoFijoDocument activoFijo = activoFijoOptinal.get();
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

		assertEquals("El formato de la fecha de compra esta mal guardada",
				format.format(LocalDateTime.now(ZoneOffset.UTC)), format.format(activoFijo.getFechaCompra()));
		assertEquals("El formato de la fecha de baja esta mal guardada",
				format.format(LocalDateTime.now(ZoneOffset.UTC).plusMonths(2)),
				format.format(activoFijo.getFechaBaja()));
		assertEquals("El estado actual esta mal", "ACTIVO", activoFijo.getEstadoActual());
		assertEquals("El color rojo es incorrecto", "AZUL", activoFijo.getColor());

		activoFijoRepository.deleteAll();
	}

	@Test(expected = AssertionError.class)
	public void construirMalActivoFijoTipoTest() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(2);

		new ActivoFijoDocument.Builder("Prueba", "No existe", "123", 1, 100, 100, 100, 100, 986.000,
				ZonedDateConvertidor.convertZonedToDate(fechaCompra),
				ZonedDateConvertidor.convertZonedToDate(fechaBaja), "activo", "azul").build();

	}

	@Test(expected = AssertionError.class)
	public void construirMalActivoFijoEstadoActualTest() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(2);

		new ActivoFijoDocument.Builder("Prueba", "Maquinaria", "123", 1, 100, 100, 100, 100, 986.000,
				ZonedDateConvertidor.convertZonedToDate(fechaCompra),
				ZonedDateConvertidor.convertZonedToDate(fechaBaja), "No existe", "azul").build();

	}

	@Test(expected = AssertionError.class)
	public void construirMalActivoFijoColorTest() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(2);

		new ActivoFijoDocument.Builder("Prueba", "Maquinaria", "123", 1, 100, 100, 100, 100, 986.000,
				ZonedDateConvertidor.convertZonedToDate(fechaCompra),
				ZonedDateConvertidor.convertZonedToDate(fechaBaja), "activo", "No existe").build();

	}

	@Test
	public void actualizarSerialInternoYFechaBajaTest() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(4);

		ActivoFijoDocument activoFijoGuardar = new ActivoFijoDocument.Builder("Prueba", "Maquinaria", "123", 1, 100,
				100, 100, 100, 986.000, ZonedDateConvertidor.convertZonedToDate(fechaCompra),
				ZonedDateConvertidor.convertZonedToDate(fechaBaja), "activo", "azul").build();
		ActivoFijoDocument activoFijoComprobar = activoFijoRepository.insert(activoFijoGuardar);

		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(activoFijoComprobar.getId()));
		Update update = new Update();
		update.set("serial", "67890");
		update.set("fechaBaja", ZonedDateConvertidor.convertZonedToDate(fechaBaja));
		mongoTemplate.findAndModify(query, update, ActivoFijoDocument.class);

		Optional<ActivoFijoDocument> activoFijoModificado = activoFijoRepository.findById(activoFijoComprobar.getId());

		assertEquals("El serial no cambio", "67890", activoFijoModificado.get().getSerial());
		assertEquals("La fecha de baja no cambio", "06 Jan 2020",
				format.format(activoFijoModificado.get().getFechaBaja()));

		activoFijoRepository.deleteAll();
	}
	
	
	@Test
	public void crearActivoFijoÁreaTest() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(2);
		
		Ciudad ciudad = new Ciudad.Builder("Bogotá").build();
		Área área = new Área.Builder("PruebaÁrea", ciudad).build();
		
		ActivoFijoDocument activoFijoGuardar = new ActivoFijoDocument.Builder("Prueba", "Maquinaria", "123", 1, 100,
				100, 100, 100, 986.000, ZonedDateConvertidor.convertZonedToDate(fechaCompra),
				ZonedDateConvertidor.convertZonedToDate(fechaBaja), "activo", "azul").área(área).build();

		ActivoFijoDocument activoFijoComprobar = activoFijoRepository.insert(activoFijoGuardar);

		Optional<ActivoFijoDocument> activoFijoOptinal = activoFijoRepository.findById(activoFijoComprobar.getId());
		ActivoFijoDocument activoFijo = activoFijoOptinal.get();
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

		assertEquals("El formato de la fecha de compra esta mal guardada",
				format.format(LocalDateTime.now(ZoneOffset.UTC)), format.format(activoFijo.getFechaCompra()));
		assertEquals("El formato de la fecha de baja esta mal guardada",
				format.format(LocalDateTime.now(ZoneOffset.UTC).plusMonths(2)),
				format.format(activoFijo.getFechaBaja()));
		assertEquals("El estado actual esta mal", "ACTIVO", activoFijo.getEstadoActual());
		assertEquals("El color rojo es incorrecto", "AZUL", activoFijo.getColor());
		
		assertEquals("El nombre de el área es incorrecta", "PruebaÁrea", activoFijo.getÁrea().getNombre());
		assertEquals("El nombre de la ciudad es incorrecto", "Bogotá", activoFijo.getÁrea().getCiudad().getNombre());

		activoFijoRepository.deleteAll();
	}
	
	
	
	@Test
	public void crearActivoFijoPersonaTest() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(2);
		
		Persona persona = new Persona.Builder("Sergio").build();
		ActivoFijoDocument activoFijoGuardar = new ActivoFijoDocument.Builder("Prueba", "Maquinaria", "123", 1, 100,
				100, 100, 100, 986.000, ZonedDateConvertidor.convertZonedToDate(fechaCompra),
				ZonedDateConvertidor.convertZonedToDate(fechaBaja), "activo", "azul").persona(persona).build();

		ActivoFijoDocument activoFijoComprobar = activoFijoRepository.insert(activoFijoGuardar);

		Optional<ActivoFijoDocument> activoFijoOptinal = activoFijoRepository.findById(activoFijoComprobar.getId());
		ActivoFijoDocument activoFijo = activoFijoOptinal.get();
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

		assertEquals("El formato de la fecha de compra esta mal guardada",
				format.format(LocalDateTime.now(ZoneOffset.UTC)), format.format(activoFijo.getFechaCompra()));
		assertEquals("El formato de la fecha de baja esta mal guardada",
				format.format(LocalDateTime.now(ZoneOffset.UTC).plusMonths(2)),
				format.format(activoFijo.getFechaBaja()));
		assertEquals("El estado actual esta mal", "ACTIVO", activoFijo.getEstadoActual());
		assertEquals("El color rojo es incorrecto", "AZUL", activoFijo.getColor());
		
		assertEquals("El nombre es incorrecto", "Sergio", activoFijo.getPersona().getNombre());

		activoFijoRepository.deleteAll();
	}

}
