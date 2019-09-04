package co.com.infrasoft.documents;

import co.com.infrasoft.documents.utilities.EstadoActual;
import co.com.infrasoft.documents.utilities.Tipo;

public class Validaciones {

	
	/**
	 * Escoger el tipo de activo
	 * @param tipo
	 * @return el string del tipo escogido
	 * @throws AssertionError cuando no existe el tipo
	 */
	private String escogerTipo(String tipo) throws AssertionError{
		String tipoEs = "";
		if(Tipo.BIENESINMUEBLES.toString().toLowerCase().equals(tipo.toLowerCase())) {
			tipoEs = tipo.toUpperCase();
		}else if(Tipo.MAQUINARIA.toString().toLowerCase().equals(tipo.toLowerCase())) {
			tipoEs = tipo.toUpperCase();
		}else if(Tipo.MATERIALOFICINA.toString().toLowerCase().equals(tipo.toLowerCase())) {
			tipoEs = tipo.toUpperCase();
		}else {
			throw new AssertionError("Tipo Desconocido: " + tipo);
		}
		return tipoEs;
	}
	
	
	/**
	 * Seleciona el estado actual del activo fijo
	 * @param estadoActual
	 * @return
	 * @throws AssertionError cuando no existe el estado actual 
	 */
	private String escogerEstadoActual(String estadoActual) throws AssertionError{
		String estadoAtualEs = "";
		if(EstadoActual.ACTIVO.toString().toLowerCase().equals(estadoActual.toLowerCase())) {
			estadoAtualEs = estadoActual.toUpperCase();
		}else if(EstadoActual.DADODEBAJA.toString().toLowerCase().equals(estadoActual.toLowerCase())) {
			estadoAtualEs = estadoActual.toUpperCase();
		}else if(EstadoActual.ENREPARACIÓN.toString().toLowerCase().equals(estadoActual.toLowerCase())) {
			estadoAtualEs = estadoActual.toUpperCase();
		}
		else if(EstadoActual.DISPONIBLE.toString().toLowerCase().equals(estadoActual.toLowerCase())) {
			estadoAtualEs = estadoActual.toUpperCase();
		}else if(EstadoActual.ASIDNADO.toString().toLowerCase().equals(estadoActual.toLowerCase())) {
			estadoAtualEs = estadoActual.toUpperCase();
		}else {
			throw new AssertionError("Estado Actual Desconocido: " + estadoActual);
		}
		return estadoAtualEs;
	}
}
