package estaciones.servicio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import bicis.modelo.Bici;
import bicis.repositorio.FiltroBusquedaBici;
import estaciones.modelo.Estacion;
import estaciones.repositorio.FiltroBusquedaEstaciones;
import historicos.modelo.Historico;
import historicos.repositorio.FiltroBusquedaHistorico;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import repositorio.RepositorioMemoria;
import servicio.FactoriaServicios;
import sitioTuristico.modelo.SitioTuristico;
import sitioTuristico.servicio.SitiosTuristicos;
import servicio.FactoriaServicios;

public class ServicioEstaciones implements IServicioEstaciones {
	
	private Repositorio<Estacion, String> repositorio = FactoriaRepositorios.getRepositorio(Estacion.class);
	private FiltroBusquedaEstaciones filtroEstacion= FactoriaRepositorios
			.getRepositorio(Estacion.class);
	
	private Repositorio<Bici, String> repoBicis = FactoriaRepositorios.getRepositorio(Bici.class);
	private Repositorio<Historico, String> repoHistorico = FactoriaRepositorios
			.getRepositorio(Historico.class);
	private FiltroBusquedaHistorico filtroHistorico = FactoriaRepositorios
			.getRepositorio(Historico.class);
	
	private SitiosTuristicos servicioTuristico = FactoriaServicios.getServicio(SitiosTuristicos.class);
	
	@Override
	public String altaEstacion(String nombre, int numeroPuestos, long dirPostal, BigDecimal longitud,
			BigDecimal latitud) throws RepositorioException {
		Estacion estacion = new Estacion(nombre, numeroPuestos, dirPostal, latitud, longitud);
		repositorio.add(estacion);
		return estacion.getId();
	}
	
	public Set<SitioTuristico> obtenerSitiosTuristicosProximos(String idEstacion) throws Exception{
		Estacion estacion;
		estacion = repositorio.getById(idEstacion);
		return servicioTuristico.obtenerSitiosInteres(estacion.getLatitud(), estacion.getLongitud());
	}

	@Override
	public void establecerSitiosTuristicos(String idEstacion, Set<SitioTuristico> sitiosTuristicos) throws RepositorioException, EntidadNoEncontrada {
		Estacion estacion = repositorio.getById(idEstacion);
		estacion.setSitiosInteres(sitiosTuristicos);
		repositorio.update(estacion);
	}

	@Override
	public Estacion obtenerEstacion(String id) throws RepositorioException, EntidadNoEncontrada {
			return repositorio.getById(id);
	}

	@Override
	public String altaBici(String modelo, String idEstacion) throws RepositorioException, EntidadNoEncontrada{
		if(modelo==null)
			throw new IllegalArgumentException("El modelo no puede ser nulo");
		if(idEstacion==null)
			throw new IllegalArgumentException("El id de la estación no puede ser nulo");
		Bici bici = new Bici(modelo, LocalDate.now());
		repoBicis.add(bici);
		String idBici = bici.getId();
		Historico historico = new Historico(idBici);
		historico.marcarEntrada(idEstacion);
		repoHistorico.add(historico);
		//Obtener la estación y decirle que ahora tiene una bici más, o sea, un sitio menos
		Estacion estacion = repositorio.getById(idEstacion);
		if(estacion.lleno())
			throw new IllegalStateException("La estación está llena");
		estacion.aparcarBici(idBici);
		repositorio.update(estacion);
		return idBici;
	}

	@Override
	public void estacionarBici(String idBici, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		if(idEstacion==null)
			throw new IllegalArgumentException("El id de la estación no puede ser nulo");
		if(idBici==null)
			throw new IllegalArgumentException("El id de la bici no puede ser nulo");
		Historico historico = filtroHistorico.getByBiciId(idBici); // Obtener historico
		if(historico.biciAparcada())
			throw new IllegalStateException("La bici ya está aparcada");
		Estacion estacion = repositorio.getById(idEstacion);
		if(estacion.lleno())
			throw new IllegalStateException("La estación está llena");
		estacion.aparcarBici(idBici);
		historico.marcarEntrada(idEstacion);
		repositorio.update(estacion);
		repoHistorico.update(historico);
	}

	@Override
	public void estacionarBici(String idBici) throws RepositorioException, EntidadNoEncontrada {
		if(idBici==null)
			throw new IllegalArgumentException("El id de la bici no puede ser nulo");
		Estacion estacion = filtroEstacion.getEstacionLibre();
		if(estacion != null)
			estacionarBici(idBici, estacion.getId());
	}

	@Override
	public void retirarBici(String idBici) throws RepositorioException, EntidadNoEncontrada {
		if(idBici==null)
			throw new IllegalArgumentException("El id de la bici no puede ser nulo");
		Historico historico = filtroHistorico.getByBiciId(idBici); // Obtener historico
		if(!historico.biciAparcada())
			throw new IllegalStateException("La bici ya está retirada");
		String idEstacion = historico.getUltimaEstacion();
		Estacion estacion = repositorio.getById(idEstacion);
		estacion.retirarBici(idBici);
		historico.marcarSalida();
		repositorio.update(estacion);
		repoHistorico.update(historico);
	}

	@Override
	public void darBajaBici(String idBici, String motivo) throws RepositorioException, EntidadNoEncontrada {
		if(idBici==null)
			throw new IllegalArgumentException("El id de la bici no puede ser nulo");
		Historico historico = filtroHistorico.getByBiciId(idBici); // Obtener historico
		Bici bici = repoBicis.getById(idBici);
		if(!historico.biciAparcada())
			throw new IllegalStateException("La bici está retirada");
		String idEstacion = historico.getUltimaEstacion();
		Estacion estacion = repositorio.getById(idEstacion);
		estacion.retirarBici(idBici);
		repositorio.update(estacion);
		repoHistorico.delete(historico);
		repoBicis.delete(bici);
	}
	
	public Set<Bici> bicisCercanas(BigDecimal longitud, BigDecimal latitud) 
			throws RepositorioException, EntidadNoEncontrada {
		filtroEstacion.getEstacionesProximas(longitud, latitud);
		/*
		Set<Estacion> estaciones = filtro
		Set<Estacion> estaciones = new HashSet<Estacion>();
		return setBicis = estaciones.stream().flatMap(e -> e.getBicisAparcadas().stream())
				.map(t -> {
					try {
						return repoBicis.getById(t);
					} catch (RepositorioException | EntidadNoEncontrada e1) {
						throw e1;
						
					}
				}).filter(b->b.isDisponible)
				.collect(Collectors.toSet());
		*/
		return null;
	}
	
	public Set<Estacion> estacionesPorNumeroSitiosTuristicos() throws RepositorioException
	{
		return repositorio.getAll().stream()
		.sorted((e1, e2) -> (e2.getSitiosInteres().size() - e1.getSitiosInteres().size()))
		.collect(Collectors.toSet());
	}

}
