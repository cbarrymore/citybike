package servicio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

		
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;


public interface IServicioEstaciones {
	
	public boolean hayHueco(String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	public void estacionarBici(String idBici, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
}
