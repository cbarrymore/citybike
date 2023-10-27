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
	
	private  Repositorio<Estacion, String> repositorio = FactoriaRepositorios.getRepositorio(Estacion.class);
	private SitiosTuristicos servicioTuristico = FactoriaServicios.getServicio(SitiosTuristicos.class);
	
	@Override
	public String altaEstacion(String nombre, int numeroPuestos, long dirPostal, BigDecimal longitud,
			BigDecimal latitud) {
		Estacion estacion = new Estacion(nombre, numeroPuestos, dirPostal, latitud, longitud);
		try {
			repositorio.add(estacion);
			return estacion.getId();
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Set<SitioTuristico> obtenerSitiosTuristicosProximos(String idEstacion){
		Estacion estacion;
		try {
			estacion = repositorio.getById(idEstacion);
			return servicioTuristico.obtenerSitiosInteres(estacion.getLatitud(), estacion.getLongitud());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public void establecerSitiosTuristicos(String idEstacion, Set<SitioTuristico> sitiosTuristicos) {
		try {
			Estacion estacion = repositorio.getById(idEstacion);
			estacion.setSitiosInteres(sitiosTuristicos);
			repositorio.update(estacion);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public Estacion obtenerEstacion(String id) {
		try {
			return repositorio.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
