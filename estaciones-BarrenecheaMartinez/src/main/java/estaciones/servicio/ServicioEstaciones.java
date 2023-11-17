package estaciones.servicio;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

import estaciones.modelo.Estacion;
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

}
