package citybike.alquiler.servicio;

import citybike.repositorio.EntidadNoEncontrada;
import citybike.repositorio.RepositorioException;
import citybike.usuario.modelo.Usuario;

public interface IServicioAlquileres {
	public void reservar(String idUsuario, String idBici) throws RepositorioException, EntidadNoEncontrada;
	public void confirmarReserva(String idUsuario) throws RepositorioException, EntidadNoEncontrada;
	public void alquilar(String idUsuario,String idBici) throws RepositorioException, EntidadNoEncontrada;
	public Usuario historialUsuario(String idUsuario) throws RepositorioException;
	public void dejarBicicleta(String idUsuario, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	public void liberarBloqueo(String idUsuario) throws RepositorioException, EntidadNoEncontrada;

}
