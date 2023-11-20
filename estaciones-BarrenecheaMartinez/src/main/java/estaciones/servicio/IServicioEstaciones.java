package estaciones.servicio;

import java.math.BigDecimal;
import java.text.Bidi;
import java.util.Collection;
import java.util.Set;

import estaciones.modelo.Estacion;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import sitioTuristico.modelo.SitioTuristico;

public interface IServicioEstaciones {
	
	public String altaEstacion(String nombre,int numeroPuestos,long dirPostal,BigDecimal longitud, BigDecimal latitud) throws RepositorioException;
	
	public Set<SitioTuristico> obtenerSitiosTuristicosProximos(String idEstacion) throws Exception;
	
	public void establecerSitiosTuristicos(String id, Set<SitioTuristico> sitiosTuristicos) throws RepositorioException, EntidadNoEncontrada;
	
	public Estacion obtenerEstacion(String id) throws RepositorioException, EntidadNoEncontrada;
	
	public String altaBici(String modelo ,String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	public void estacionarBici(String idBici, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	public void estacionarBici(String idBici) throws RepositorioException, EntidadNoEncontrada;
	
}
