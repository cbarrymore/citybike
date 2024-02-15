package repositorio;

import java.util.List;

import modelo.Usuario;
import persistencia.UsuarioEntidad;

public class RepositorioUsuario implements RepositorioString<Usuario>
{

	Repositorio<UsuarioEntidad, String> repoEntidad = new RepositorioUsuarioEntidadJPA();//Esto se cambia
	@Override
	public String add(Usuario entity) throws RepositorioException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Usuario entity) throws RepositorioException, EntidadNoEncontrada
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Usuario entity) throws RepositorioException, EntidadNoEncontrada
	{
		//Conversión
		repoEntidad.delete(null);
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
	
}
