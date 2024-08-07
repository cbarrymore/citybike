package citybike.alquiler.servicio;

import java.io.IOException;

import citybike.estaciones.servicio.ServicioEstacionesException;
import citybike.repositorio.EntidadNoEncontrada;
import citybike.repositorio.RepositorioException;
import citybike.usuario.modelo.Usuario;

public interface IServicioAlquileres {
	public void reservar(String idUsuario, String idBici) throws RepositorioException, EntidadNoEncontrada, IOException, ServicioEstacionesException;

	public void confirmarReserva(String idUsuario) throws RepositorioException, EntidadNoEncontrada;

	public void alquilar(String idUsuario, String idBici) throws RepositorioException, EntidadNoEncontrada, IOException, ServicioEstacionesException;

	public Usuario historialUsuario(String idUsuario) throws RepositorioException;

	public void dejarBicicleta(String idUsuario, String idEstacion)
			throws RepositorioException, EntidadNoEncontrada, IOException, ServicioEstacionesException;

	public void eliminarReservaDeBici(String idBici) throws RepositorioException, EntidadNoEncontrada;

	public void liberarBloqueo(String idUsuario) throws RepositorioException, EntidadNoEncontrada;

	void cancelarReserva(String idUsuario) throws RepositorioException, EntidadNoEncontrada;

}
