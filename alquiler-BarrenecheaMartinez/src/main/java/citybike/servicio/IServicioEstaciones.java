package servicio;
	
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;


public interface IServicioEstaciones {
	
	public boolean huecoDisponible(String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	public void estacionarBici(String idBici, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
}
