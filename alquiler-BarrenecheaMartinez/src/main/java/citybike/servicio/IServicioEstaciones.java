package citybike.servicio;
	
import citybike.repositorio.EntidadNoEncontrada;
import citybike.repositorio.RepositorioException;


public interface IServicioEstaciones {
	
	public boolean huecoDisponible(String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	public void estacionarBici(String idBici, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
}
