package servicio;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServicioAlquileres {
	public void reservar(String idUsuario, String idBici) throws RepositorioException, EntidadNoEncontrada;
	public void confirmarReserva(String idUsuario) throws RepositorioException;
	public void alquilar(String idUsuario,String idBici) throws RepositorioException;
	public void historialUsuario(String idUsuario);
	public void dejarBicicleta(String idUsuario, String idEstacion) throws RepositorioException;
	public void liberarBloqueo(String idUsuario) throws RepositorioException;
}
