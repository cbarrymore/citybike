package incidencias.servicio;

import java.time.LocalDate;
import java.util.List;

import bicis.modelo.Bici;
import bicis.modelo.Estado;
import bicis.modelo.Incidencia;
import bicis.repositorio.FiltroBusquedaBici;
import estaciones.modelo.Estacion;
import estaciones.servicio.IServicioEstaciones;
import estaciones.servicio.ServicioEstaciones;
import historicos.modelo.Historico;
import historicos.repositorio.FiltroBusquedaHistorico;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import repositorio.RepositorioString;
import servicio.FactoriaServicios;

public class ServicioIncidenciasBase implements ServicioIncidencias {
	
	private RepositorioString<Bici> repoBici = FactoriaRepositorios.getRepositorio(Bici.class);
	private IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	
	private FiltroBusquedaBici filtroBici= FactoriaRepositorios
			.getRepositorio(Bici.class);
	@Override
	public void crearIncidencia(String idBici, String descripcion) throws RepositorioException, EntidadNoEncontrada {
		if(idBici == null) {
			throw new IllegalArgumentException("La id de la bici no puede ser nula");
		}
		if(descripcion.isBlank()) {
			throw new IllegalArgumentException("La descripción debe estár en blanco");
		}
		Incidencia incidencia = new Incidencia(descripcion,idBici);
		Bici bici = repoBici.getById(idBici);
		
		bici.setDisponible(false);
		bici.setIncidencia(incidencia);
		
		repoBici.update(bici);
		
	}

	@Override
	public List<Incidencia> recuperarIncidenciasAbiertas() {
		List<Bici> bicis = filtroBici.getBicisConIncidencias();
		return bicis.stream()
				.map(b -> b.getIncidencia())
				.filter(i -> i.getEstado() != Estado.CANCELADO || i.getEstado() != Estado.RESUELTA )
				.toList();
	}

	@Override
	public void cancelarIncidencia(String idBici, String motivo) throws RepositorioException, EntidadNoEncontrada {
		Bici bici = repoBici.getById(idBici);
		Incidencia incidencia = bici.getIncidencia();
		if (incidencia.getEstado() != Estado.PENDIENTE) {
			throw new IllegalStateException("La bici con id "+ idBici + "no no se puede cancelar porque no está pendiente");
		}
		incidencia.setDescripcion(motivo);
		incidencia.setEstado(Estado.CANCELADO);
		incidencia.setFechaCierre(LocalDate.now());
		bici.setDisponible(true);
	}

	@Override
	public void asignarIncidencia(String idBici, String operario) throws RepositorioException, EntidadNoEncontrada {
		Bici bici = repoBici.getById(idBici);
		Incidencia incidencia = bici.getIncidencia();
		if (incidencia.getEstado() != Estado.PENDIENTE) {
			throw new IllegalStateException("La bici con id "+ idBici + "no no se puede asignar porque no está pendiente");
		}
		incidencia.setDescripcion(operario);
		incidencia.setEstado(Estado.ASIGNADA);
		incidencia.setFechaCierre(LocalDate.now());
		servicioEstaciones.retirarBici(idBici);
		
	}

	@Override
	public void resolverIncidencia(String idBici, String motivo,boolean reparada) throws RepositorioException, EntidadNoEncontrada {
		if(idBici == null) {
			throw new IllegalArgumentException("La id de la bici no puede ser nula");
		}
		if(motivo.isBlank()) {
			throw new IllegalArgumentException("La descripción debe estár en blanco");
		}
		Bici bici = repoBici.getById(idBici);
		Incidencia incidencia = bici.getIncidencia();
		if (incidencia.getEstado() != Estado.ASIGNADA) {
			throw new IllegalStateException("La bici con id "+ idBici + "no no se puede resolver porque no está asignada");
		}
		if(reparada) {
			servicioEstaciones.estacionarBici(idBici);
		}
		else
			servicioEstaciones.darBajaBici(idBici,motivo);
		
	}

}
