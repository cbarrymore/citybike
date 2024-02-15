package repositorio;

import persistencia.UsuarioEntidad;

public class RepositorioUsuarioEntidadJPA extends RepositorioJPA<UsuarioEntidad>
{

	@Override
	public Class<UsuarioEntidad> getClase() {
		// TODO Auto-generated method stub
		return UsuarioEntidad.class;
	}

	@Override
	public String getNombre() {
		return UsuarioEntidad.class.getName().substring(UsuarioEntidad.class.getName().lastIndexOf(".") + 1);
	}

}
