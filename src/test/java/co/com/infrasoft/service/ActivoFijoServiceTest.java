package co.com.infrasoft.service;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.infrasoft.IntrasoftApplication;
import co.com.infrasoft.documents.ActivoFijoDocument;
import co.com.infrasoft.documents.convertidor.ZonedDateConvertidor;
import co.com.infrasoft.repository.ActivoFijoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { IntrasoftApplication.class })
public class ActivoFijoServiceTest {

	@Autowired
	private ActivoFijoService activoFijoService;
	
	private final ZoneId zona = ZonedDateConvertidor.zona();
	
	@Autowired
	private ActivoFijoRepository activoFijoRepository;
	
	
	@Test(expected = IllegalArgumentException.class)
	public void crearActivoFijoMalTest() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTime fechaCompra = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaBaja = fechaCompra.plusMonths(2);
		ActivoFijoDocument activoFijo = new ActivoFijoDocument.Builder("Prueba", "Maquinaria", "123", 1, 100, 100,
				100, 100, 986.000, ZonedDateConvertidor.convertZonedToDate(fechaCompra), ZonedDateConvertidor.convertZonedToDate(fechaBaja), "activo", "azul").build();
		activoFijoService.crearActivoFijo(activoFijo);
	}
	
	@Test
	public void crearActivoFijoTest() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTime fechaBaja = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaCompra = fechaBaja.plusMonths(2);
		ActivoFijoDocument activoFijo = new ActivoFijoDocument.Builder("Prueba", "Maquinaria", "123", 1, 100, 100,
				100, 100, 986.000, ZonedDateConvertidor.convertZonedToDate(fechaCompra), ZonedDateConvertidor.convertZonedToDate(fechaBaja), "activo", "azul").build();
		activoFijoService.crearActivoFijo(activoFijo);
		activoFijoRepository.deleteAll();
	}
	
	@Test
	public void encontrarActivoFijoTest() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTime fechaBaja = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaCompra = fechaBaja.plusMonths(2);
		ActivoFijoDocument activoFijo = new ActivoFijoDocument.Builder("Prueba", "Maquinaria", "123", 1, 100, 100,
				100, 100, 986.000, ZonedDateConvertidor.convertZonedToDate(fechaCompra), ZonedDateConvertidor.convertZonedToDate(fechaBaja), "activo", "azul").build();
		ObjectId id = activoFijoService.crearActivoFijo(activoFijo);
		ActivoFijoDocument activo = activoFijoService.encontrarActivoFijo(id);
		assertNotNull(activo);
	}
	
	@Test(expected = NullPointerException.class)
	public void encontrarActivoFijoNoExisteTest() {
		activoFijoService.encontrarActivoFijo(new ObjectId("5d70e1ad79d45517b3212e14"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void encontrarActivoFijoConObjectIDNoValidoTest() {
		activoFijoService.encontrarActivoFijo(new ObjectId("ATSDEV"));
	}
	
	@Test
	public void actualizarActivoFijoTest() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTime fechaBaja = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaCompra = fechaBaja.plusMonths(2);
		ActivoFijoDocument activoFijo = new ActivoFijoDocument.Builder("Prueba", "Maquinaria", "123", 1, 100, 100,
				100, 100, 986.000, ZonedDateConvertidor.convertZonedToDate(fechaCompra), ZonedDateConvertidor.convertZonedToDate(fechaBaja), "activo", "azul").build();
		ObjectId id = activoFijoService.crearActivoFijo(activoFijo);
		activoFijoService.actualizarActivoFijo(id, "456789", fecha.plusMonths(1));
		activoFijoRepository.deleteAll();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void actualizarActivoFijoFechaIncorrectaTest() {
		LocalDateTime fecha = LocalDateTime.now(ZoneOffset.UTC);
		ZonedDateTime fechaBaja = ZonedDateTime.of(fecha, zona);
		ZonedDateTime fechaCompra = fechaBaja.plusMonths(2);
		ActivoFijoDocument activoFijo = new ActivoFijoDocument.Builder("Prueba", "Maquinaria", "123", 1, 100, 100,
				100, 100, 986.000, ZonedDateConvertidor.convertZonedToDate(fechaCompra), ZonedDateConvertidor.convertZonedToDate(fechaBaja), "activo", "azul").build();
		ObjectId id = activoFijoService.crearActivoFijo(activoFijo);
		activoFijoService.actualizarActivoFijo(id, "456789", fecha.plusMonths(5));
		activoFijoRepository.deleteAll();
	}


}
