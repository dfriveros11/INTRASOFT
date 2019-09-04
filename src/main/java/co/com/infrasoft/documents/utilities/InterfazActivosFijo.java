package co.com.infrasoft.documents.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.com.infrasoft.documents.ActivoFijoDocument;

public interface InterfazActivosFijo {

	List<ActivoFijoDocument> activosFijos = new ArrayList<ActivoFijoDocument>();
	
	default Optional<ActivoFijoDocument> buscar(int id) {
		Optional<ActivoFijoDocument> activoFijoE = Optional.of(null);
		boolean result = false;
		for (ActivoFijoDocument activoFijo : activosFijos) {
			if(true) {
				result = true;
				activoFijoE = Optional.of(activoFijo);
			}
			if(result) {
				break;
			}
		}
		return activoFijoE;
	}
	
	void crearNuevosActivos(ActivoFijoDocument activoFijo);
	
	void actualizarActivos(int id);
	
}
