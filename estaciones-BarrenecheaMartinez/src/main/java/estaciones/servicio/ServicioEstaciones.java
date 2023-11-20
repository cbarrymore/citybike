package estaciones.servicio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import bicis.modelo.Bici;
import estaciones.modelo.Estacion;
import historicos.modelo.Historico;
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
	private Repositorio<Bici, String> repoBicis = FactoriaRepositorios.getRepositorio(Bici.class);
	private Repositorio<Historico, String> repoHistorico = FactoriaRepositorios
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
		Bici bici = new Bici(modelo, LocalDate.now());
		repoBicis.add(bici);
		String idBici = bici.getId();
		Historico historico = new Historico(idBici);
		historico.marcarEntrada(idEstacion);
		repoHistorico.add(historico);
		//Obtener la estación y decirle que ahora tiene una bici más, o sea, un sitio menos
		Estacion estacion = repositorio.getById(idEstacion);
		if(estacion.lleno())
			throw new IllegalStateException();
		estacion.aparcarBici(idBici);
		repositorio.update(estacion);
		return idBici;
	}

	@Override
	public void estacionarBici(String idBici, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		Historico historico = repoHistorico.getById(idBici); // Obtener historico
		if(historico.biciAparcada())
			throw new IllegalStateException();
		Estacion estacion = repositorio.getById(idEstacion);
		if(estacion.lleno())
			throw new IllegalStateException();
		estacion.aparcarBici(idBici);
		historico.marcarSalida();
		repositorio.update(estacion);
		repoHistorico.update(historico);
	}

	@Override
	public void estacionarBici(String idBici) throws RepositorioException, EntidadNoEncontrada {
		// TODO Buscar la estación
		String idEstacion = "";
		estacionarBici(idBici, idEstacion);
		
	}

}
