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
		if(descripcion == null) {
			throw new IllegalArgumentException("La descripción no puede ser nula");
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
		if(idBici == null) {
			throw new IllegalArgumentException("La id de la bici no puede ser nula");
		}
		if(motivo == null) {
			throw new IllegalArgumentException("El motivo no puede ser nula");
		}
		Bici bici = repoBici.getById(idBici);
		Incidencia incidencia = bici.getIncidencia();
		if (incidencia.getEstado() != Estado.PENDIENTE) {
			throw new IllegalStateException("La bici con id "+ idBici + "no se puede cancelar porque no está pendiente");
		}
		incidencia.setDescripcion(motivo);
		incidencia.setEstado(Estado.CANCELADO);
		incidencia.setFechaCierre(LocalDate.now());
		bici.setDisponible(true);
		bici.setIncidencia(incidencia);
		repoBici.update(bici);
		bici.setIncidencia(incidencia);
	}

	@Override
	public void asignarIncidencia(String idBici, String operario) throws RepositorioException, EntidadNoEncontrada {
		if(idBici == null) {
			throw new IllegalArgumentException("La id de la bici no puede ser nula");
		}
		if(operario== null) {
			throw new IllegalArgumentException("Se debe indicar un operario");
		}
		Bici bici = repoBici.getById(idBici);
		Incidencia incidencia = bici.getIncidencia();
		if (incidencia.getEstado() != Estado.PENDIENTE) {
			throw new IllegalStateException("La bici con id "+ idBici + "no no se puede asignar porque no está pendiente");
		}
		incidencia.setDescripcion(operario);
		incidencia.setEstado(Estado.ASIGNADA);
		incidencia.setFechaCierre(LocalDate.now());
		servicioEstaciones.retirarBici(idBici);
		bici.setIncidencia(incidencia);
		repoBici.update(bici);
		bici.setIncidencia(incidencia);
	}

	@Override
	public void resolverIncidencia(String idBici, String motivo,boolean reparada) throws RepositorioException, EntidadNoEncontrada {
		if(idBici == null) {
			throw new IllegalArgumentException("La id de la bici no puede ser nula");
		}
		if(motivo == null) {
			throw new IllegalArgumentException("La descripción debe estár en blanco");
		}
		Bici bici = repoBici.getById(idBici);
		Incidencia incidencia = bici.getIncidencia();
		if (incidencia.getEstado() != Estado.ASIGNADA) {
			throw new IllegalStateException("La bici con id "+ idBici + "no se puede resolver porque no está asignada");
		}
		incidencia.setEstado(Estado.RESUELTA);
		incidencia.setFechaCierre(LocalDate.now());
		incidencia.setDescripcion(motivo);
		bici.setIncidencia(incidencia);
		if(reparada) {
			repoBici.update(bici);
			bici.setIncidencia(incidencia);
			servicioEstaciones.estacionarBici(idBici);
		}
		else
			servicioEstaciones.darBajaBici(idBici,motivo);
		
	}

}
