package citybike.servicio;

import citybike.repositorio.EntidadNoEncontrada;
import citybike.repositorio.RepositorioException;

public class ServicioEstacionesStub implements IServicioEstaciones {

	@Override
	public boolean huecoDisponible(String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void estacionarBici(String idBici, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub

	}

}