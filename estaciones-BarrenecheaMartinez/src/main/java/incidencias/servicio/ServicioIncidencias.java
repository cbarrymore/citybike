package incidencias.servicio;

import java.util.List;

import bicis.modelo.Estado;
import bicis.modelo.Incidencia;

public interface ServicioIncidencias {
	
	public void crearIncidencia(String idBici, String descripcion);
	public void gestionarIncidencia(String idBici, Estado estado);
	public List<Incidencia> recuperarIncidenciasAbiertas();
}
