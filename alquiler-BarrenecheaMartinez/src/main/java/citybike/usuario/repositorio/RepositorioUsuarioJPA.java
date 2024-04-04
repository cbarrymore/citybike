package citybike.usuario.repositorio;

import citybike.persistencia.Entidad;
import citybike.repositorio.RepositorioJPA;
import citybike.usuario.modelo.Usuario;
import citybike.usuario.persistencia.UsuarioEntidad;

public class RepositorioUsuarioJPA extends RepositorioJPA<Usuario, UsuarioEntidad>
{

	@Override
	public Class<UsuarioEntidad> getClase() {
		return UsuarioEntidad.class;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return UsuarioEntidad.class.getName().substring(UsuarioEntidad.class.getName().lastIndexOf(".") + 1);
	}

	@Override
	protected Entidad<Usuario> getEntidad(Usuario object)
	{
		return new UsuarioEntidad(object);
	}

}
