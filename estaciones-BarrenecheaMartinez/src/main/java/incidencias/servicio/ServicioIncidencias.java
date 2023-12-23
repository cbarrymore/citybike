package incidencias.servicio;

import java.util.List;

import bicis.dto.IncidenciaDTO;
import bicis.modelo.Incidencia;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface ServicioIncidencias {
	
	public void crearIncidencia(String idBici, String descripcion) throws RepositorioException, EntidadNoEncontrada;
	public void cancelarIncidencia(String idBici,String motivo) throws RepositorioException, EntidadNoEncontrada;
	public void asignarIncidencia(String idBici,String operario) throws RepositorioException, EntidadNoEncontrada;
	public void resolverIncidencia(String idBici,String motivo,boolean reparada) throws RepositorioException, EntidadNoEncontrada;	
	public List<Incidencia> recuperarIncidenciasAbiertas();
	public List<IncidenciaDTO> recuperarIncidenciasAbiertasDTO();
}
