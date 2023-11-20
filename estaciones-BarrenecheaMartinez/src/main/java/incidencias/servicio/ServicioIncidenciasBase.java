package incidencias.servicio;

import java.util.List;

import bicis.modelo.Estado;
import bicis.modelo.Incidencia;

public class ServicioIncidenciasBase implements ServicioIncidencias {

	@Override
	public void crearIncidencia(String idBici, String descripcion) {
		i = new Incidencia()
	}

	@Override
	public void gestionarIncidencia(String idBici, Estado estado) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Incidencia> recuperarIncidenciasAbiertas() {
		// TODO Auto-generated method stub
		return null;
	}

}
