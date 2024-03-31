package estaciones2.estacion.servicio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import estaciones2.bici.modelo.Bici;
import estaciones2.estacion.modelo.Estacion;
import estaciones2.repositorio.EntidadNoEncontrada;
import estaciones2.repositorio.RepositorioException;

public interface IServicioEstaciones {
	
	public String altaEstacion(String nombre,int numeroPuestos,long dirPostal,BigDecimal longitud, BigDecimal latitud) throws RepositorioException;
	
	public Estacion obtenerEstacion(String id) throws RepositorioException, EntidadNoEncontrada;
	
	public String altaBici(String modelo ,String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	public void retirarBici(String idBici) throws RepositorioException, EntidadNoEncontrada;
	
	public void darBajaBici(String idBici, String motivo) throws RepositorioException, EntidadNoEncontrada;
	
	public List<Bici> bicisEstacion(String idEstaciones) throws RepositorioException, EntidadNoEncontrada;
	
	public List<Bici> bicisEstacionLimitado(String idEstaciones) throws RepositorioException, EntidadNoEncontrada;

	public List<Estacion> obtenerEstaciones() throws RepositorioException;
}
