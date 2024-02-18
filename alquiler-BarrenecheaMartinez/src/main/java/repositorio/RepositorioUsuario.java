package repositorio;

import java.util.List;

import modelo.Alquiler;
import modelo.Reserva;
import modelo.Usuario;
import persistencia.AlquilerEntidad;
import persistencia.ReservaEntidad;
import persistencia.UsuarioEntidad;

public class RepositorioUsuario implements RepositorioString<Usuario>
{

	Repositorio<UsuarioEntidad, String> repoEntidad = FactoriaRepositorios.getRepositorio(UsuarioEntidad.class);
	@Override
	public String add(Usuario entity) throws RepositorioException
	{
		UsuarioEntidad ue = crearEntidad(entity);
		return repoEntidad.add(ue);
	}

	@Override
	public void update(Usuario entity) throws RepositorioException, EntidadNoEncontrada
	{
		UsuarioEntidad ue = crearEntidad(entity);
		repoEntidad.update(ue);
		
	}

	@Override
	public void delete(Usuario entity) throws RepositorioException, EntidadNoEncontrada
	{
		UsuarioEntidad ue = crearEntidad(entity);
		repoEntidad.delete(ue);
	}

	@Override
	public Usuario getById(String id) throws RepositorioException, EntidadNoEncontrada
	{
		return repoEntidad.getById(id).getObject();
	}

	@Override
	public List<Usuario> getAll() throws RepositorioException
	{
		return repoEntidad.getAll().stream().map(e -> e.getObject()).toList();
	}

	@Override
	public List<String> getIds() throws RepositorioException
	{
		return repoEntidad.getIds();
	}
	
	private UsuarioEntidad crearEntidad(Usuario usuario)
	{
		UsuarioEntidad u = new UsuarioEntidad();
		u.setId(usuario.getId());
		u.setAlquileres(usuario.getAlquileres().stream().map(a -> crearEntidadAlquiler(a)).toList());
		u.setReservas(usuario.getReservas().stream().map(r -> crearEntidadReserva(r)).toList());
		return u;
	}
	
	private AlquilerEntidad crearEntidadAlquiler(Alquiler alquiler)
	{
		AlquilerEntidad entidad = new AlquilerEntidad();
		entidad.setIdBici(alquiler.getIdBici());
		entidad.setInicio(alquiler.getInicio());
		entidad.setFin(alquiler.getFin());
		return entidad;
	}
	
	private ReservaEntidad crearEntidadReserva(Reserva reserva)
	{
		ReservaEntidad entidad = new ReservaEntidad();
		entidad.setIdBici(reserva.getIdBici());
		entidad.setCreada(reserva.getCreada());
		entidad.setCaducidad(reserva.getCaducidad());
		return entidad;
	}
	
}
