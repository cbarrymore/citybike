package citybike.estaciones.servicio;

import java.io.IOException;

import citybike.repositorio.EntidadNoEncontrada;
import citybike.repositorio.RepositorioException;

public interface IServicioEstaciones {

	public boolean huecoDisponible(String idEstacion)
			throws RepositorioException, EntidadNoEncontrada, IOException, ServicioEstacionesException;

	public void estacionarBici(String idBici, String idEstacion)
			throws RepositorioException, EntidadNoEncontrada, IOException, ServicioEstacionesException;

	public boolean biciDisponible(String idBici) 
			throws RepositorioException, EntidadNoEncontrada, IOException, ServicioEstacionesException;
}
